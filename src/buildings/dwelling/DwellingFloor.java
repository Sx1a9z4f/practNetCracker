package buildings.dwelling;

import buildings.Floor;
import buildings.Space;
import buildings.sup.FloorIterator;

import java.util.Iterator;

public class DwellingFloor implements Floor, Cloneable {
    private Space[] spaces;

    public DwellingFloor(int flatsNum) {
        spaces = new Space[flatsNum];
        for (int i = 0; i < spaces.length; i++)
            spaces[i] = new Flat();
    }

    public DwellingFloor(Space...spaces) {
        this.spaces = spaces;
    }

    @Override
    public int getSpaceNum() {
        return spaces.length;
    }

    @Override
    public int getAreas() {
        int allArea = 0;
        for (Space space : spaces) {
            allArea += space.getArea();
        }
        return allArea;
    }

    @Override
    public int getRooms() {
        int allRoom = 0;
        for (Space space : spaces) {
            allRoom += space.getRoom();
        }
        return allRoom;
    }

    @Override
    public Space[] getSpaces() {
        return spaces;
    }

    @Override
    public Space getSpace(int num) {
        return spaces[num];
    }

    @Override
    public void setSpace(int num, Space space) {
        spaces[num] = space;
    }

    @Override
    public void addSpace(int num, Space space) {
        Space[] spacesSec = new Space[spaces.length + 1];
        if (num > spaces.length) {
            System.arraycopy(spaces, 0, spacesSec, 0, spaces.length);
            spacesSec[num] = space;
        } else
            for (int i = 0; i < spacesSec.length; i++) {
                if (i < num) {
                    spacesSec[i] = spaces[i];
                } else if (i == num) {
                    spacesSec[i] = space;
                }
                if (i > num)
                    spacesSec[i] = spaces[i - 1];
            }
        spaces = spacesSec;
    }

    @Override
    public void deleteSpace(int num) {
        Space[] spacesSec = new Space[spaces.length - 1];
        for (int i = 0; i < spacesSec.length; i++) {
            if (i < num)
                spacesSec[i] = spaces[i];
            else
                spacesSec[i] = spaces[i + 1];

        }
        spaces = spacesSec;
    }

    @Override
    public Space getBestSpace() {
        Space space = null;
        double bestArea = 0;
        for (Space value : spaces) {
            if (value.getArea() > bestArea) {
                bestArea = value.getArea();
                space = value;
            }
        }
        return space;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < getSpaceNum(); i++) {
            str.append(((Flat) getSpace(i)).toString());
            if (i < getSpaceNum() - 1)
                str.append(", ");
        }
        return "DwellingFloor" + " (" + getSpaceNum() + ", " + str.toString() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        boolean res = false;
        if (obj instanceof DwellingFloor && ((DwellingFloor) obj).getSpaceNum() == getSpaceNum())
            for (int i = 0; i < getSpaceNum(); i++, res = true)
                if (!((DwellingFloor) obj).getSpace(i).equals(getSpace(i)))
                    return false;
        return res;
    }

    @Override
    public int hashCode() {
        int result = 0;
        Integer spacesNum = getSpaceNum();
        for (Space space: spaces)
            result += spacesNum.byteValue() ^ space.hashCode();
        return result;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Object clone = super.clone();
        Space[] space = new Space[getSpaceNum()];
        for (int i = 0; i < getSpaceNum(); i++)
            space[i] = (Space) ((Floor) clone).getSpace(i).clone();
        return new DwellingFloor(space);
    }

    @Override
    public Iterator iterator() {
        return new FloorIterator(this);
    }

    @Override
    public int compareTo(Floor floor) {
        return Integer.compare(getSpaceNum(), floor.getSpaceNum());
    }
}
