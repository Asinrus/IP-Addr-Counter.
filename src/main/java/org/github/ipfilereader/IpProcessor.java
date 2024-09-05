package org.github.ipfilereader;

import java.nio.ByteBuffer;
import java.util.function.IntConsumer;

public class IpProcessor {

    private static final char DOT = '.';
    public static final int SEGMENT_BIT_SIZE = 8;
    public static final char ZERO = '0';
    public static final char NINE = '9';
    public static final int LAST_SEGMENT = 3;
    private static final int[][] MULT_CACHE = preCalcMultiplication();

    private final IntConsumer action;

    public IpProcessor(IntConsumer action) {
        this.action = action;
    }

    public void process(ByteBuffer b) {
        int curIp = 0;
        int curSegment = 0;
        int segmentNumber = 0;
        while (b.hasRemaining()) {
            char c = (char) b.get();
            if (isDigit(c)) {
                curSegment = MULT_CACHE[curSegment][c];
            } else {
                if (c == DOT) {
                    curIp <<= SEGMENT_BIT_SIZE;
                    curIp |= curSegment;
                    curSegment = 0;
                    segmentNumber++;
                } else {
                    if (segmentNumber == LAST_SEGMENT) {
                        curIp <<= SEGMENT_BIT_SIZE;
                        curIp |= curSegment;
                        action.accept(curIp);
                        curSegment = 0;
                        segmentNumber = 0;
                        curIp = 0;
                    }
                }
            }
        }
    }

    private static int[][] preCalcMultiplication() {
        int[][] multCache = new int[1 << 8][1 << 8];
        for (int i = 0; i < multCache.length; i++) {
            for (int d = ZERO; d <= NINE; d++) {
                multCache[i][d] = i * 10 + (d - ZERO);
            }
        }
        return multCache;
    }

    private static boolean isDigit(char c) {
        return ZERO <= c && c <= NINE;
    }
}
