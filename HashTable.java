public class HashTable{
    private ImageFeature[] ifTable;
    private int buckets;

    public HashTable(int buckets){
        this.buckets = buckets;
        this.ifTable = new ImageFeature[buckets];
    }

    public int hash(String name){
        int hashval = 0;
        for(int i=0;i<name.length();i++){
            hashval = hashval + (int) name.charAt(i);//adds all the letters into a value
        }
        if(hashval<0){
            hashval = - hashval;//cant be neg
        }
        return hashval % buckets;//makes the key
    }

    public void insert(ImageFeature image){
        int key = hash(image.name);
        while(ifTable[key]!= null){
            key = (key+1)%buckets;//itterate till an empty spot is found
        }
        ifTable[key]= image;
    }

    public int search(String name){
        int key = hash(name);
        int start = key;
        while(ifTable[key]!= null){
            if(ifTable[key].name.equals(name)){//if the name is the same return curr key/i
                return key;
            }
            key = (key+1)%buckets;
            if(key == start){
                break;//feature not there
            }
        }
        return -1;//nothing found
    }

    public void remove(ImageFeature image){
        int key = search(image.name);
        if(key == -1){
            System.out.println("ImageFeature not Found, Unable to Remove");
            return;
        }
        ifTable[key] = null;
    }

    public ImageFeature returnFeature(String name){
        int key = search(name);
        if(key == -1){
            System.out.println("ImageFeature not found, unable to return");
            return null;
        }
        return ifTable[key];
    }

    public void printTable(){
        for(int i = 0; i< buckets ;i++){
            if(ifTable[i]!=null){
                System.out.println(i+": "+ ifTable[i].name);
            }else{
                System.out.println(i+": x");//null means emprty
            }
        }
    }
}