package Model.value;

import Model.types.IType;
import Model.types.RefType;

public class RefValue implements IValue {
    Integer address;
    IType locationType;

    public RefValue(int address, IType locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    @Override
    public IValue deepCopy() {
        return new RefValue(address, locationType);
    }

    @Override
    public String toString() {
        return "(" + address + ", " + locationType + ")";
    }


    public int getAddress() {
        return address;
    }

    public IType getLocationType() {
        return locationType;
    }
}
