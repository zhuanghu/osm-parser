package br.zuq.osm.parser.model;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.io.WKBWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Willy Tiengo
 */
public class Relation extends AbstractNode {

    private OSM osm;
    public List<Member> members;
    public Map<String, String> tags;

    public Relation(OSM osm, String id, String visible, String timestamp,
            String version, String changeset, String user,
            String uid, List<Member> members, Map<String, String> tags) {
        
        super(id, visible, timestamp, version, changeset, user, uid);
        this.osm = osm;
        this.members = members;
        this.tags = tags;
    }

    /**
     * @return The MultiLineString of all ways members of this relation. If any
     *         way members can not be found in the datase, returns
     *         <code>null</code>.
     */
    public MultiLineString getMultiLineString() {
        Way way;
        List<LineString> lines = new ArrayList<LineString>();

        for (Member member : members) {
            if (isWay(member)) {
                way = osm.getWay(member.ref);

                if (way == null) {
                    return null;
                }

                lines.add(way.getLineString());
            }
        }

        return new GeometryFactory().createMultiLineString(
                lines.toArray(new LineString[0]));
    }

    public boolean isBoundary() {
        return tags.get("boundary") != null;
    }

    public int getAdminLevel() {
        return Integer.parseInt(tags.get("admin_level"));
    }

    public String getName() {
        return tags.get("name");
    }

    public String getShape() {
        MultiLineString mls = getMultiLineString();
        return (mls != null) ? WKBWriter.bytesToHex(new WKBWriter().write(mls)) : null;
    }

    private boolean isWay(Member m) {
        return m.type.equals("way");
    }
}
