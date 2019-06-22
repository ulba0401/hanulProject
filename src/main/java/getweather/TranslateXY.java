package getweather;

public class TranslateXY {
    // LCC DFS ��ǥ��ȯ�� ���� ���� �ڷ�
    //
    double RE = 6371.00877; // ���� �ݰ�(km)
    double GRID = 5.0;      // ���� ����(km)
    double SLAT1 = 30.0;    // ���� ����1(degree)
    double SLAT2 = 60.0;    // ���� ����2(degree)
    double OLON = 126.0;    // ������ �浵(degree)
    double OLAT = 38.0;     // ������ ����(degree)
    double XO = 43;         // ������ X��ǥ(GRID)
    double YO = 136;        // ��1���� Y��ǥ(GRID)
    TranslatexyVO tvo = new TranslatexyVO();
    //
    // LCC DFS ��ǥ��ȯ ( code : "toXY"(���浵->��ǥ, v1:����, v2:�浵), "toLL"(��ǥ->���浵,v1:x, v2:y) )
    //
    public TranslatexyVO getTransXY (double v1, double v2){
        double DEGRAD = Math.PI / 180.0;
        double RADDEG = 180.0 / Math.PI;
        String code = "toXY";
        double re = RE / GRID;
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olon  = OLON  * DEGRAD;
        double olat  = OLAT  * DEGRAD;

        double sn = Math.tan( Math.PI*0.25 + slat2*0.5 ) / Math.tan( Math.PI*0.25 + slat1*0.5 );
        sn = Math.log( Math.cos(slat1) / Math.cos(slat2) ) / Math.log(sn);
        double sf = Math.tan( Math.PI*0.25 + slat1*0.5 );
        sf = Math.pow(sf,sn) * Math.cos(slat1) / sn;
        double ro = Math.tan( Math.PI*0.25 + olat*0.5 );
        ro = re * sf / Math.pow(ro,sn);
        if (code == "toXY")
        {
            tvo.setLat(v1);
            tvo.setLng(v2);
            double ra = Math.tan( Math.PI*0.25 + (v1)*DEGRAD*0.5 );
            ra = re * sf / Math.pow(ra,sn);
            double theta = v2 * DEGRAD - olon;
            if (theta >  Math.PI) theta -= 2.0 * Math.PI;
            if (theta < -Math.PI) theta += 2.0 * Math.PI;
            theta *= sn;
            tvo.setX((int) Math.floor( ra*Math.sin(theta) + XO + 0.5 ));;
            tvo.setY((int) Math.floor( ro - ra*Math.cos(theta) + YO + 0.5 ));
        }

        return tvo;
    }
    //-->
}
