
public class Pierwotny {
    public static void rozwiaz(FunkcjaCelu f , Ograniczenie ogr1 , Ograniczenie ogr2 , Ograniczenie ogr3){

        PunktWspolny p1 = PunktWspolny.znajdzWspolny(ogr1,ogr2);
        PunktWspolny p2 = PunktWspolny.znajdzWspolny(ogr2,ogr3);
        PunktWspolny p3 = PunktWspolny.znajdzWspolny(ogr1,ogr3);
        boolean p1ok=false,p2ok=false,p3ok=false;

        if(ogr1.bigger && p2.Y>=-(p2.X*ogr1.wsp[0]+ogr1.wsp[2])/ogr1.wsp[1])p2ok=true;
        if(ogr2.bigger && p3.Y>=-(p3.X*ogr2.wsp[0]+ogr2.wsp[2])/ogr2.wsp[1])p3ok=true;
        if(ogr3.bigger && p1.Y>=-(p1.X*ogr3.wsp[0]+ogr3.wsp[2])/ogr3.wsp[1])p1ok=true;
        if(!ogr1.bigger && p2.Y<=-(p2.X*ogr1.wsp[0]+ogr1.wsp[2])/ogr1.wsp[1])p2ok=true;
        if(!ogr2.bigger && p3.Y<=-(p3.X*ogr2.wsp[0]+ogr2.wsp[2])/ogr2.wsp[1])p3ok=true;
        if(!ogr3.bigger && p1.Y<=-(p1.X*ogr3.wsp[0]+ogr3.wsp[2])/ogr3.wsp[1])p1ok=true;

        System.out.println("Punkty wspólne:");
        if(p1ok)p1.wypisz();
        if(p2ok)p2.wypisz();
        if(p3ok)p3.wypisz();

        System.out.println("Wartości w punktach:");
        if(p1ok)System.out.println("Wartosc= "+f.wartosc(p1));
        if(p2ok)System.out.println("Wartosc= "+f.wartosc(p2));
        if(p3ok)System.out.println("Wartosc= "+f.wartosc(p3));

        if(p1ok&&p2ok&&p3ok)(f.wybierzOptymalny(p1,p2,p3)).wypisz();
        if(p1ok&&p2ok)(f.wybierzOptymalny(p1,p2)).wypisz();
        if(p1ok&&p3ok)(f.wybierzOptymalny(p1,p3)).wypisz();
        if(p2ok&&p3ok)(f.wybierzOptymalny(p2,p3)).wypisz();

    }

    public static void rozwiazDualny(FunkcjaCelu f , Ograniczenie ogr1 , Ograniczenie ogr2 , Ograniczenie ogr3){

        PunktWspolny p1 = PunktWspolny.znajdzWspolny(ogr1,ogr2);
        PunktWspolny p2 = PunktWspolny.znajdzWspolny(ogr2,ogr3);
        PunktWspolny p3 = PunktWspolny.znajdzWspolny(ogr1,ogr3);
        //boolean p1ok=false,p2ok=false,p3ok=false;

        System.out.println("Punkty wspólne:");
        p1.wypisz();
        p2.wypisz();
        p3.wypisz();

        System.out.println("Wartości w punktach:");
        System.out.println("Wartosc= "+f.wartosc(p1));
        System.out.println("Wartosc= "+f.wartosc(p2));
        System.out.println("Wartosc= "+f.wartosc(p3));

        (f.wybierzOptymalny(p1,p2,p3)).wypisz();

    }

}
