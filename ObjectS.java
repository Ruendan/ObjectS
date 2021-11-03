import java.lang.reflect.Field;

public abstract class ObjectS extends Object {
    
    @Override
    public String toString(){
        return this.toString(0);
    }
    
    private String toString(int tab){
        StringBuilder sb = new StringBuilder();
        sb.append("{" + System.lineSeparator());
        for(Field f : this.getClass().getDeclaredFields()){
            f.setAccessible(true);
            sb.append(getTabs(tab+1) + f.getName() + "(" + f.getType() + ") : " +   getValue(f, tab)     );
            sb.append(System.lineSeparator());
        }
        sb.append(getTabs(tab) + "}");
        return sb.toString();
    }
    
    private String getValue(Field f, int tab){
        try {
            if(f.get(this) instanceof Object[]) {
                StringBuilder sb = new StringBuilder();
                sb.append("[" + System.lineSeparator());
                for(Object o : (Object[]) f.get(this)){
                    sb.append(getTabs(tab+2)+(( o instanceof ObjectS )?( (ObjectS) o ).toString(tab +2) : o) + "," + System.lineSeparator());
                }
                sb.append(getTabs(tab+1) + "]");
                return sb.toString();
            } else return ""+(( f.get(this) instanceof ObjectS )?( (ObjectS) f.get(this) ).toString(tab +1) : f.get(this));
        } catch (IllegalArgumentException | IllegalAccessException e) {
            return "##private-field##";
        }
    }
    
    private String getTabs(int lim){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<lim; i++) sb.append("\t");
        return sb.toString();
    }
}
