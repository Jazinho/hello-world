

public class PunktWspolny {
    double X;
    double Y;

    public PunktWspolny(double x, double y) {
        X = x;
        Y = y;
    }

    public void wypisz(){
        System.out.println("X = "+ X + ", Y= " + Y);
    }

    public static PunktWspolny znajdzWspolny(Ograniczenie ogr1,Ograniczenie ogr2){

//        if(ogr1.wsp[0]==0.0 && ogr2.wsp[1]==0.0)
//            return new PunktWspolny(ogr2.wsp[2]/ogr2.wsp[0] , ogr1.wsp[2]/ogr1.wsp[1]);
//
//        if(ogr2.wsp[0]==0.0 && ogr1.wsp[1]==0.0)
//            return new PunktWspolny(ogr1.wsp[2]/ogr1.wsp[0] , ogr2.wsp[2]/ogr2.wsp[1]);
//
//        if(ogr1.wsp[0]==0.0)
//            return new PunktWspolny( (ogr2.wsp[2]-(ogr2.wsp[1]*(ogr1.wsp[2])/ogr1.wsp[1]))/ogr2.wsp[0] , ogr1.wsp[2]/ogr1.wsp[1] );
//
//        if(ogr1.wsp[1]==0.0)
//            return new PunktWspolny( ogr1.wsp[2]/ogr1.wsp[0] , (ogr2.wsp[2]-(ogr2.wsp[0]*(ogr1.wsp[2]/ogr1.wsp[0])))/ogr2.wsp[1] );
//
//        if(ogr2.wsp[0]==0.0)
//            return new PunktWspolny( (ogr1.wsp[2]-(ogr1.wsp[1]*(ogr2.wsp[2])/ogr2.wsp[1]))/ogr1.wsp[0] , ogr2.wsp[2]/ogr2.wsp[1] );
//
//        if(ogr2.wsp[1]==0.0)
//            return new PunktWspolny( ogr2.wsp[2]/ogr2.wsp[0] , (ogr1.wsp[2]-(ogr1.wsp[0]*(ogr2.wsp[2]/ogr2.wsp[0])))/ogr1.wsp[1] );

        //System.out.println("Jestem "+ogr1.wsp[0]+" "+ogr1.wsp[1]+" "+ogr1.wsp[2] + " "+ogr2.wsp[0]+" "+ogr2.wsp[1]+" "+ogr2.wsp[2]);

        double Wx= ((-ogr1.wsp[2])*ogr2.wsp[1]) - ((-ogr2.wsp[2])*ogr1.wsp[1]);
        double Wy= (ogr1.wsp[0]*(-ogr2.wsp[2])) - (ogr2.wsp[0]*(-ogr1.wsp[2]));
        double W= (ogr1.wsp[0]*ogr2.wsp[1]) - (ogr2.wsp[0]*ogr1.wsp[1]);
        //System.out.println("x: "+Wx/W+" y: "+Wy/W);
        return new PunktWspolny(Wx/W,Wy/W);
    }

}
