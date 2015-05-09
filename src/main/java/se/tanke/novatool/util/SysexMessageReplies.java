package se.tanke.novatool.util;

import static se.tanke.novatool.util.NovaSystemConstants.REPLY_NON_REAL_TIME_BYTE;

import javax.sound.midi.SysexMessage;

import se.tanke.novatool.Configuration;

/**
 * This class handle sysex messages replies.
 */
public final class SysexMessageReplies {
    
    private SysexMessageReplies() {
    }
    
    /**
     * Check if this is a reply that indicates an invalid request.
     * @param config The configuration
     * @param sysex The sysex to check
     * @return <code>true</code> if the reply is a reply that indicates an invalid request
     */
    public static boolean isEmptyOrInvalidPresetReply(final Configuration config, final SysexMessage sysex) {
        return MidiUtil.matches(sysex, SysexMessage.SYSTEM_EXCLUSIVE,
                REPLY_NON_REAL_TIME_BYTE,
                config.getSysExId(),
                NovaSystemConstants.NAK,
                0); // Dummy
    }
}
