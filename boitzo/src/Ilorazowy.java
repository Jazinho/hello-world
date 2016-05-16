
public class Ilorazowy {
    public static void rozwiaz(FunkcjaCelu f1 , FunkcjaCelu f2, Ograniczenie ogr1 , Ograniczenie ogr2 , Ograniczenie ogr3){

        PunktWspolny p1 = PunktWspolny.znajdzWspolny(ogr1,ogr2);
        PunktWspolny p2 = PunktWspolny.znajdzWspolny(ogr2,ogr3);
        PunktWspolny p3 = PunktWspolny.znajdzWspolny(ogr1,ogr3);

        double obszarXmin=p1.X,obszarXmax=p1.X,obszarYmin=p1.Y,obszarYmax=p1.Y;

        if(p2.X>obszarXmax){obszarXmax=p2.X;}else{obszarXmin=p2.X;}
        if(p3.X>obszarXmax){obszarXmax=p3.X;}else{obszarXmin=p3.X;}
        if(p2.Y>obszarYmax){obszarYmax=p2.Y;}else{obszarYmin=p2.Y;}
        if(p3.Y>obszarYmax){obszarYmax=p3.Y;}else{obszarYmin=p3.Y;}

        double[] tab,tab2;

        if(f1.wsp.length == 2){
            tab=new double[]{f1.wsp[0],f1.wsp[1],0.0};
        }else{
            tab=new double[]{f1.wsp[0],f1.wsp[1],f1.wsp[2]};
        }

        if(f2.wsp.length == 2){
            tab2=new double[]{f2.wsp[0],f2.wsp[1],0.0};
        }else{
            tab2=new double[]{f2.wsp[0],f2.wsp[1],f2.wsp[2]};
        }

        Ograniczenie funOgr1= new Ograniczenie(tab);
        Ograniczenie funOgr2= new Ograniczenie(tab2);

        PunktWspolny glowny = PunktWspolny.znajdzWspolny(funOgr1,funOgr2);

        double[] tab3=new double[]{-(p1.Y-glowny.Y), p1.X-glowny.X, (p1.Y*( p1.X-glowny.X)-p1.X*(p1.Y-glowny.Y))/(p1.X-glowny.X)};

        Ograniczenie glownyZP1 = new Ograniczenie(tab3);

        PunktWspolny czyWewnatrz = PunktWspolny.znajdzWspolny(glownyZP1,ogr3);

        if(czyWewnatrz.X<obszarXmax && czyWewnatrz.X > obszarXmin && czyWewnatrz.Y<obszarYmax && czyWewnatrz.Y>obszarYmin){
            System.out.println("Rozważamy p2("+p2.X+";"+p2.Y+") i p3("+p3.X+";"+p3.Y+")");
            System.out.println("Optymalny:");
            (f1.wybierzOptymalny(p2,p3)).wypisz();
            return;
        }

        double[] tab4=new double[]{-(p2.Y-glowny.Y), p2.X-glowny.X, (p2.Y*( p2.X-glowny.X)-p2.X*(p2.Y-glowny.Y))/(p2.X-glowny.X)};

        Ograniczenie glownyZP2 = new Ograniczenie(tab4);

        PunktWspolny czyWewnatrz2 = PunktWspolny.znajdzWspolny(glownyZP2,ogr1);

        if(czyWewnatrz2.X<obszarXmax && czyWewnatrz2.X > obszarXmin && czyWewnatrz2.Y<obszarYmax && czyWewnatrz2.Y>obszarYmin){
            System.out.println("Rozważamy p1("+p1.X+";"+p1.Y+") i p3("+p3.X+";"+p3.Y+")");
            System.out.println("Optymalny:");
            (f1.wybierzOptymalny(p1,p3)).wypisz();
            return;
        }

        double[] tab5=new double[]{-(p3.Y-glowny.Y), p3.X-glowny.X, (p3.Y*( p3.X-glowny.X)-p3.X*(p3.Y-glowny.Y))/(p3.X-glowny.X)};

        Ograniczenie glownyZP3 = new Ograniczenie(tab5);

        PunktWspolny czyWewnatrz3 = PunktWspolny.znajdzWspolny(glownyZP3,ogr2);

        if(czyWewnatrz3.X<obszarXmax && czyWewnatrz3.X > obszarXmin && czyWewnatrz3.Y<obszarYmax && czyWewnatrz3.Y>obszarYmin){
            System.out.println("Rozważamy p1("+p1.X+";"+p1.Y+") i p2("+p2.X+";"+p2.Y+")");
            System.out.println("Optymalny:");
            (f1.wybierzOptymalny(p1,p2)).wypisz();
            return;
        }

//        glowny.wypisz();//obszary OK
//        System.out.println("Obszary: "+obszarXmax+" "+obszarXmin+" "+obszarYmax+" "+obszarYmin);
//        System.out.println("GlownyZP1: "+glownyZP1.wsp[0]+" "+glownyZP1.wsp[1]+" "+glownyZP1.wsp[2]);
//        System.out.println("GlownyZP2: "+glownyZP2.wsp[0]+" "+glownyZP2.wsp[1]+" "+glownyZP2.wsp[2]);
//        System.out.println("GlownyZP3: "+glownyZP3.wsp[0]+" "+glownyZP3.wsp[1]+" "+glownyZP3.wsp[2]); //punkty są OK
//        czyWewnatrz.wypisz();
//        czyWewnatrz2.wypisz();
//        czyWewnatrz3.wypisz();

        System.out.println("Nieprzewidziany wypadek !!!!!!!!");
        return;

    }
}
