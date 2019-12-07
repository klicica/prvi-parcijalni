package ba.unsa.etf.rpr;

import java.util.*;
import java.util.function.Function;

public class Preduzece {
    private int osnovica;
    private ArrayList<RadnoMjesto> radnaMjesta = new ArrayList<>();

    public Preduzece(int osnovica) throws NeispravnaOsnovica {
        postaviOsnovicu(osnovica);
    }

    public int dajOsnovicu() {
        return osnovica;
    }

    public void postaviOsnovicu(int osnovica) throws NeispravnaOsnovica {
        if(osnovica <= 0) throw new NeispravnaOsnovica("Neispravna osnovica " + osnovica);
        this.osnovica = osnovica;
    }

    public void novoRadnoMjesto(RadnoMjesto radnoMjesto) {
        radnaMjesta.add(radnoMjesto);
    }

    public Map<RadnoMjesto, Integer> sistematizacija() {
        HashMap<RadnoMjesto, Integer> sistemat = new HashMap<>();
        for(RadnoMjesto radnoMjesto : radnaMjesta) {
            Integer broj = sistemat.get(radnoMjesto);
            if(broj == null) {
                sistemat.put(radnoMjesto, 1);
            }
            else {
                sistemat.put(radnoMjesto, broj+1);
            }
        }
        return sistemat;
    }

    public void zaposli(Radnik radnik, String nazivRadnogMjesta) {
        for(RadnoMjesto radnoMjesto : radnaMjesta) {
            if(radnoMjesto.getNaziv().equals(nazivRadnogMjesta) && radnoMjesto.getRadnik() == null) {
                radnoMjesto.setRadnik(radnik);
                return;
            }
        }
        throw new IllegalStateException("Nijedno radno mjesto tog tipa nije slobodno");
    }

    public Set<Radnik> radnici() {
        TreeSet<Radnik> rezultat = new TreeSet<>();
        for(RadnoMjesto radnoMjesto : radnaMjesta) {
            if(radnoMjesto.getRadnik() != null) {
                rezultat.add(radnoMjesto.getRadnik());
            }
        }
        return rezultat;
    }

    public double iznosPlate() {
        double rezultat = 0;
        for(RadnoMjesto radnoMjesto : radnaMjesta) {
            if(radnoMjesto.getRadnik() != null) rezultat += osnovica * radnoMjesto.getKoeficijent();
        }
        return rezultat;
    }

    public void obracunajPlatu() {
        for(RadnoMjesto radnoMjesto : radnaMjesta) {
            if(radnoMjesto.getRadnik() != null) {
                radnoMjesto.getRadnik().dodajPlatu(osnovica * radnoMjesto.getKoeficijent());
            }
        }
    }

    public List<Radnik> filterRadnici(Function<Radnik,Boolean> funkcija) {
        ArrayList<Radnik> rezultat = new ArrayList<>();
        for(RadnoMjesto radnoMjesto : radnaMjesta) {
            if(radnoMjesto.getRadnik() != null && funkcija.apply(radnoMjesto.getRadnik())) {
                rezultat.add(radnoMjesto.getRadnik());
            }
        }
        return rezultat;
    }

    public List<Radnik> vecaProsjecnaPlata(double prosjecnaPlata) {
        return filterRadnici((Radnik r) -> { return r.prosjecnaPlata() > prosjecnaPlata; });
    }
}
