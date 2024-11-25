
import java.util.ArrayList;
import java.util.List;

public class Venue {
    private int id;
    private String venueName;
    private String venueLocation;
    private int venueCapacity;
    private boolean booked;
    private String venueAddress;
    private static List<Venue> venueList = new ArrayList<>();

    public Venue() {
    }

    public Venue(int id, String venueName, String venueLocation, int venueCapacity, boolean booked, String venueAddress) {
        this.id = id;
        this.venueName = venueName;
        this.venueLocation = venueLocation;
        this.venueCapacity = venueCapacity;
        this.booked = booked;
        this.venueAddress = venueAddress;
    }

    public static void createVenueList() {

        Venue venue1 = new Venue(1, "CyberArena Malaysia", "Kuala Lumpur", 1000,
                false, "334 Persiaran Tekong, Kuala Lumpur, Malaysia");
        Venue venue2 = new Venue(2, "eSports Nexus Center", "Selangor", 500,
                false, "Jalan Ahkao, Selangor, Malaysia");
        Venue venue3 = new Venue(3, "GamingHub Arena", "Perlis", 100,
                false, "Jalan Sembilan Square, Perlis, Malaysia.");
        Venue venue4 = new Venue(4, "MyGameZone eSports Arena", "Pulau Penang", 150,
                false, "Jalan rooster, Pulau Penang, Malaysia");
        Venue venue5 = new Venue(5, "ProGamer Dome", "Perak", 200,
                false, "Jalan TT, Perak, Malaysia");

        venueList.add(venue1);
        venueList.add(venue2);
        venueList.add(venue3);
        venueList.add(venue4);
        venueList.add(venue5);

    }

    public static List<Venue> getVenueList() {
        return venueList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getVenueLocation() {
        return venueLocation;
    }

    public void setVenueLocation(String venueLocation) {
        this.venueLocation = venueLocation;
    }

    public int getVenueCapacity() {
        return venueCapacity;
    }

    public void setVenueCapacity(int venueCapacity) {
        this.venueCapacity = venueCapacity;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public String getVenueAddress() {
        return venueAddress;
    }

    public void setVenueAddress(String venueAddress) {
        this.venueAddress = venueAddress;
    }

//    @Override
//    public String toString(){
//        System.out.printf("%-4s %-35s%-15s%-10s%-10s%-50s\n","Opt","Name","Location","Capacity","Booked","Address");
//        System.out.printf("%-4d %-34s %-14s %-9s %-9s %-49s\n",this.getId(),this.getVenueName(),this.getVenueLocation(),
//                this.getVenueCapacity(),this.isBooked(),this.getVenueAddress());
//        return "";
//    }

    public String toString() {
        StringBuilder venue = new StringBuilder();
        venue.append("┌─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐\n");
        venue.append(String.format("│ %-4s │ %-40s │ %-30s │ %-20s │ %-45s │\n",
                "Id", "Venue Name", "Venue Location", "Venue Capacity", "Venue Address"));
        venue.append("├─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘\n");
        venue.append(String.format("│ %-4d │ %-40s │ %-30s │ %-20s │ %-45s │\n",
                this.getId(), this.getVenueName(), this.getVenueLocation(), this.getVenueCapacity(), this.getVenueAddress()));
        venue.append("└─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘");
        return venue.toString();

    }
}