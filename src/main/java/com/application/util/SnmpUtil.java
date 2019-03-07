package com.application.util;

import com.application.model.IfTableRow;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TableEvent;
import org.snmp4j.util.TableUtils;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class SnmpUtil {
    private static final Map<String, BiFunction<TableEvent, IfTableRow, IfTableRow>> OIDS_TO_FIELDS = new HashMap<>();
    static {
        OIDS_TO_FIELDS.put("1.3.6.1.2.1.2.2.1.1",
                (tableEvent, ifTableRow) -> {
                    ifTableRow.setIfIndex(tableEvent.getColumns()[0].getVariable().toInt());
                    return ifTableRow;
        });
        OIDS_TO_FIELDS.put("1.3.6.1.2.1.2.2.1.2",
                (tableEvent, ifTableRow) -> {
                    ifTableRow.setIfDescr(tableEvent.getColumns()[0].getVariable().toString());
                    return ifTableRow;
                });
    }

    // iso.org.dod.internet.
    // +-- mgmt.
    //       +-- mib2.system
    //                    +-- .interfaces
    //                              +-- .ifTable
    //                                       +---.ifEntry = .1.3.6.1.2.1.2.2.1
    //
    // https://www.alvestrand.no/objectid/1.3.6.1.2.1.2.2.1.html
    //
    private static final String IF_TABLE_START = ".1.3.6.1.2.1.2.2.1";
    private static final String LOWER_BOUND   = "1.0"; // + ifIndex
    private static final String UPPER_BOUND   = "2.28"; // + ifDescr
    private static final int SNMP_PORT = 161;

    private static OID getVctStartOID(int portIdx) {
        return new OID(".1.3.6.1.4.1.40418.7.100.3.2.1.18." + portIdx); // + "+ \" i 1\""
    }

    private static OID getVctResultOID(int portIdx) {
        return new OID(".1.3.6.1.4.1.40418.7.100.3.2.1.19." + portIdx);
    }


    private static Snmp snmp;

    private Map<Integer, IfTableRow> ifTable;

    public SnmpUtil(Snmp snmp) {
        this.snmp = snmp;
        ifTable = new HashMap<>();
    }

    public Collection<IfTableRow> getIfTable(String ip) throws Exception {
        TableUtils tUtils = new TableUtils(snmp, new DefaultPDUFactory());
        List<TableEvent> interfaces = tUtils.getTable(
                getTarget(ip, SNMP_PORT),
                new OID[] {new OID(IF_TABLE_START)}, new OID(LOWER_BOUND), new OID(UPPER_BOUND));

        interfaces.stream().forEachOrdered(evt -> {
            String oid = evt.getColumns()[0].getOid().toString().substring(0, 19); // OID part for OIDS_TO_FIELDS map
            BiFunction callback = OIDS_TO_FIELDS.get(oid);
            int tableIdx = evt.getIndex().get(1); // Table row index

            IfTableRow i = ifTable.get(tableIdx);
            if (i == null) {
                i = new IfTableRow();
                ifTable.put(tableIdx, i);
            }

            if (callback == null) {
                return;
            }
            callback.apply(evt, i);
        });
        return ifTable.values();
    }

    private Optional<ResponseEvent> sendPDU(Target target, Consumer<PDU> mutatePduCallback) throws Exception {
        PDU pdu = new PDU();
        mutatePduCallback.accept(pdu);
        Optional<ResponseEvent> event = Optional.ofNullable(snmp.send(pdu, target));

//        if (event == null) {
//            throw new RuntimeException("Target timed out");
//        }

        return event;
    }

    public void startVCT(String ip, int physicalPort) throws Exception {
        OID oid = getVctStartOID(physicalPort);
        Optional<ResponseEvent> event = sendPDU(getTarget(ip, SNMP_PORT), pdu -> {
            pdu.setType(PDU.SET);
            pdu.add(new VariableBinding(getVctStartOID(physicalPort), new Integer32(1)));
        });
        event.orElseThrow(() -> new RuntimeException("Target + " + ip + " timed out"));
    }

    public String getVCT(String ip, int physicalPort) throws Exception {
        OID oid = getVctResultOID(physicalPort);
        Optional<ResponseEvent> event = sendPDU(getTarget(ip, SNMP_PORT), pdu -> {
            pdu.setType(PDU.GET);
            pdu.add(new VariableBinding(getVctResultOID(physicalPort)));
        });
        event.orElseThrow(() -> new RuntimeException("Target " + ip + " timed out"));

        return event.map(this::getString).get();
    }

    private String getString(ResponseEvent event) {
        return event.getResponse()
                .getVariableBindings()
                .get(0)
                .getVariable()
                .toString();
    }

    private Target getTarget(String ip, int port) {
        Address targetAddress = GenericAddress.parse(String.join("", "udp:", ip, "/", String.valueOf(port)));
        CommunityTarget target = new CommunityTarget();
        target.setAddress(targetAddress);
        target.setCommunity(new OctetString("public"));
        target.setRetries(3);
        target.setTimeout(1500);
        target.setVersion(SnmpConstants.version2c);
        return target;
    }


}
