package al420445.airport;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name="PASSENGERS")
@Getter @Setter
public class Passenger {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME", unique = true)
    private String name;

    @ManyToOne
    private Airport airport;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "passenger", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    public Passenger(String name) {
        this.name = name;
    }

    // Requis obligatoirement pour JPA
    public Passenger() {
    }

    public List<Ticket> getTickets() {
        return Collections.unmodifiableList(tickets);
    }

    // Fait la double association
    public void addTicket(Ticket ticket) {
        ticket.setPassenger(this);
        tickets.add(ticket);
    }


    @Override
    public String toString() {
        return "Passenger{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", airport=" + airport +
                '}';
    }
}
