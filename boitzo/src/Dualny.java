
public class Dualny {

    public static void rozwiaz(FunkcjaCelu f , Ograniczenie ogr1 , Ograniczenie ogr2){

        double[] tab0 = new double[]{ -ogr1.wsp[3] , -ogr2.wsp[3] }; //funkcja celu

        double[] tab1 = new double[]{ ogr1.wsp[0] , ogr2.wsp[0] , -f.wsp[0] };
        double[] tab2 = new double[]{ ogr1.wsp[1] , ogr2.wsp[1] , -f.wsp[1] };
        double[] tab3 = new double[]{ ogr1.wsp[2] , ogr2.wsp[2] , -f.wsp[2] };

        FunkcjaCelu fun = new FunkcjaCelu(tab0,!f.toMax);
        Ograniczenie o1 = new Ograniczenie(tab1,f.toMax);
        Ograniczenie o2 = new Ograniczenie(tab2,f.toMax);
        Ograniczenie o3 = new Ograniczenie(tab3,f.toMax);
//        System.out.println(""+f.wsp[0]+f.wsp[1]+f.wsp[2]);
//        System.out.println(""+o1.wsp[0]+o1.wsp[1]+o1.wsp[2]);
//        System.out.println(""+o2.wsp[0]+o2.wsp[1]+o2.wsp[2]);
//        System.out.println(""+o3.wsp[0]+o3.wsp[1]+o3.wsp[2]);

        Pierwotny.rozwiaz(fun,o1,o2,o3);
    }
}
