package Model.value;

import Model.types.IType;
import Model.types.StringType;

public class StringValue implements IValue{
    String value;

    @Override
    public IType getType() {
        return new StringType();
    }

    public StringValue(String s) {this.value = s;}

    public StringValue() {value = "";}

    @Override
    public String toString() {return value;}

    public String getValue() {return value;}

    @Override
    public IValue deepCopy() {
        return new StringValue(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof StringValue that))
            return false;
        return value.equals(that.value);

    }
}
