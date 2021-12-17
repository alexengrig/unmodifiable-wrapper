/*
 * Copyright 2021 Alexengrig Dev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.alexengrig.util.annotation.unmodifiablewrapper;

import dev.alexengrig.util.annotation.UnmodifiableWrapper;

import java.util.Objects;

@UnmodifiableWrapper
public class AllTypesDomain {

    private boolean booleanValue;
    private byte byteValue;
    private short shortValue;
    private char charValue;
    private int intValue;
    private float floatValue;
    private double doubleValue;
    private long longValue;
    private String stringValue;

    public boolean isBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public byte getByteValue() {
        return byteValue;
    }

    public void setByteValue(byte byteValue) {
        this.byteValue = byteValue;
    }

    public short getShortValue() {
        return shortValue;
    }

    public void setShortValue(short shortValue) {
        this.shortValue = shortValue;
    }

    public char getCharValue() {
        return charValue;
    }

    public void setCharValue(char charValue) {
        this.charValue = charValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public float getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(float floatValue) {
        this.floatValue = floatValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public long getLongValue() {
        return longValue;
    }

    public void setLongValue(long longValue) {
        this.longValue = longValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AllTypesDomain that = (AllTypesDomain) o;
        return booleanValue == that.booleanValue
                && byteValue == that.byteValue
                && shortValue == that.shortValue
                && charValue == that.charValue
                && intValue == that.intValue
                && Float.compare(that.floatValue, floatValue) == 0
                && Double.compare(that.doubleValue, doubleValue) == 0
                && longValue == that.longValue
                && Objects.equals(stringValue, that.stringValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(booleanValue, byteValue, shortValue, charValue, intValue, floatValue, doubleValue, longValue, stringValue);
    }

    @Override
    public String toString() {
        return "AllTypesDomain{" +
                "booleanValue=" + booleanValue +
                ", byteValue=" + byteValue +
                ", shortValue=" + shortValue +
                ", charValue=" + charValue +
                ", intValue=" + intValue +
                ", floatValue=" + floatValue +
                ", doubleValue=" + doubleValue +
                ", longValue=" + longValue +
                ", stringValue='" + stringValue + '\'' +
                '}';
    }

}
