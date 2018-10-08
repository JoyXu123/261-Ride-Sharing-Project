public class ConsPair {
    public int extra_distance;
    public Rider first;
    public Rider second;
    public ConsPair(){
        extra_distance=0;
        first=null;
        second=null;
    }
    public ConsPair(int dis, Rider a1, Rider a2){
        extra_distance=dis;
        first=a1;
        second=a2;
    }
}
