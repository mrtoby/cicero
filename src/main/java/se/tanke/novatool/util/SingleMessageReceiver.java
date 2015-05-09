package se.tanke.novatool.util;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

public class SingleMessageReceiver implements Receiver {

    private MidiMessage lastReceivedMessage = null;
    private long lastTimeStamp = -1;
    private int numberOfReceivedMessages = 0;
    private boolean closed = false;
    
    public synchronized void send(final MidiMessage message, final long timeStamp) {
        if (closed) {
            throw new IllegalStateException("Closed");
        }
        lastReceivedMessage = message;
        lastTimeStamp = timeStamp;
        numberOfReceivedMessages++;
        notifyAll();
    }

    public synchronized void close() {
        closed = true;
    }

    public synchronized MidiMessage getLastReceivedMessage() {
        return lastReceivedMessage;
    }

    public synchronized long getLastTimeStamp() {
        return lastTimeStamp;
    }

    public synchronized int getNumberOfReceivedMessages() {
        return numberOfReceivedMessages;
    }

    public synchronized boolean isClosed() {
        return closed;
    }

    public synchronized boolean hasReceivedMessage() {
        return numberOfReceivedMessages > 0;
    }

    public synchronized boolean waitForMessage(long timeout) throws InterruptedException {
        wait(timeout);
        return numberOfReceivedMessages > 0;
    }
}
