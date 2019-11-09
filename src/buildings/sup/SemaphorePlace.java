package buildings.sup;

import buildings.Floor;

public class SemaphorePlace {
    private int curMax;
    private int cur;
    private Floor lock;

    public SemaphorePlace(int curMax) {
        this.curMax = curMax;
    }

    public void enter(Floor floor) {
        lock = floor;
        synchronized (lock) {
            cur++;
            if (cur > curMax) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    System.out.println("Interrupted");
                }
            }
        }

    }

    public void leave(Floor floor) {
        lock = floor;
        synchronized (lock) {
            cur--;
            lock.notify();
        }

    }



}
