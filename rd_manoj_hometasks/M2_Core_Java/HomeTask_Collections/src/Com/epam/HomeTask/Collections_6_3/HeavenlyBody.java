package Com.epam.HomeTask.Collections_6_3;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class HeavenlyBody {

    private final String name;
    private final double orbitalPeriod;
    private final Set<HeavenlyBody> satellites;

    public HeavenlyBody(String name, double orbitalPeriod) {
        this.name = name;
        this.orbitalPeriod = orbitalPeriod;
        this.satellites = new HashSet<>();
    }
    

    public String getName() {
        return name;
    }

    public double getOrbitalPeriod() {
        return orbitalPeriod;
    }

    public boolean addMoon(HeavenlyBody moon) {
        return this.satellites.add(moon);
    }

    public Set<HeavenlyBody> getSatellites() {
        return new HashSet<>(this.satellites);
    }
    
    @Override 
    public int hashCode() {
    	System.out.println("hashcode called");
    	return Objects.hash(name,orbitalPeriod,satellites);
    }
}
