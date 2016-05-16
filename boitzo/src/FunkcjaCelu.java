public class FunkcjaCelu {
    double[] wsp;
    boolean toMax;

    public FunkcjaCelu(double[] wsp, boolean toMax) {
        this.wsp = wsp;
        this.toMax = toMax;
    }

    public double wartosc(PunktWspolny p){
        return (wsp[0]*p.X) + (wsp[1]*p.Y);
    }

    public PunktWspolny wybierzOptymalny(PunktWspolny p1,PunktWspolny p2,PunktWspolny p3){
        if(toMax) {
            if (wartosc(p1) >= wartosc(p2) && wartosc(p1) >= wartosc(p3)) return p1;
            if (wartosc(p2) >= wartosc(p1) && wartosc(p2) >= wartosc(p3)) return p2;
            return p3;
        }else {
            if (wartosc(p1) <= wartosc(p2) && wartosc(p1) <= wartosc(p3)) return p1;
            if (wartosc(p2) <= wartosc(p1) && wartosc(p2) <= wartosc(p3)) return p2;
            return p3;
        }
    }

    public PunktWspolny wybierzOptymalny(PunktWspolny p1,PunktWspolny p2){
        if(toMax) {
            if (wartosc(p1) >= wartosc(p2)) return p1;
            return p2;
        }else {
            if (wartosc(p1) <= wartosc(p2)) return p1;
            return p2;
        }
    }

}
