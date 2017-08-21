/*******************************************************************************
 * Copyright (c) 2017 Eurotech and/or its affiliates and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Eurotech - initial API and implementation
 *******************************************************************************/
package org.eclipse.kapua.commons.util;

import java.lang.reflect.Constructor;
import java.util.Date;
import java.util.Random;

import org.eclipse.kapua.KapuaIllegalArgumentException;
import org.eclipse.kapua.KapuaIllegalNullArgumentException;
import org.junit.Assert;
import org.junit.Test;

public class ArgumentValidatorTest extends Assert {

    @Test
    public void testConstructor() throws Exception {
        Constructor<ArgumentValidator> argvalidator = ArgumentValidator.class.getDeclaredConstructor();
        argvalidator.setAccessible(true);
        argvalidator.newInstance();
    }

    @Test
    public void testMatchNotNull() throws KapuaIllegalArgumentException {
        String argRegExp = "^[a-zA-Z0-9\\-]{3,}$";
        String argVal = null;
        try {
            ArgumentValidator.match(argVal, argRegExp, "Null_test_case");
        } catch (Exception ex) {
            fail("No exception expected for: " + argVal);
        }
    }

    @Test
    public void testIllegalCharacterSimpleName() throws Exception {
        String argRegExprSimpleName = "^[a-zA-Z0-9\\-]{3,}$";
        String permittedSymbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890-";
        String[] listOfFalseStringsSimpleName = new String[] { "abc!", "abc\\", "abc#", "abc$", "abc%", "abc&", "abc'", "abc(",
                "abc)", "abc=", "abc?", "abc*", "abc_", "abc:", "abc;", "abc>", "abc<", "abc.", "abc,", "abc¡", "abc™", "abc£",
                "abc¢", "abc∞", "abc§", "abc¶", "abc•", "abcª", "abcº", "abc≠", "abcœ", "abc∑", "abc´", "abc®", "abc†", "abc—",
                "abc¨", "abc^", "abcø", "abcπ", "abc[", "abc]", "abcå", "abcß", "abc∂", "abcƒ", "abc©", "abc ", "̏abc", "abc∆", "abc¬",
                "abc…", "abc^", "abc\\", "abcΩ", "abc≈", "abcç", "abc√", "abc∫", "abc~", "abcµ", "abc≤", "abc≥", "abc÷", "abc⁄", "abc@",
                "abc‹", "abc›", "abc€", "abcı", "abc°", "abc·", "abc‚", "abc_", "abc±", "abcŒ", "abc„", "abc‘", "abc”", "abc’", "abcÉ",
                "abcØ", "abc∏", "abc{", "abc}", "abcÅ", "abcÍ", "abcÔ", "abc", "abcÒ", "abcæ", "abcÆ", "abc|", "abc«", "abc◊", "abcÑ",
                "abc¯", "abcÈ", "abcˇ", "abc¿", "", "a", "ab" };
        int sizeOfFalseStrings = listOfFalseStringsSimpleName.length;
        String[] listOfPermittedStringsSimpleName = new String[] { permittedSymbols, "abc", "ABC", "123", "-123", "-1aB2", "---" };
        int sizeOfPermittedStrings = listOfPermittedStringsSimpleName.length;
        // Negative tests
        for (int i = 0; i < sizeOfFalseStrings; i++) {
            try {
                ArgumentValidator.match(listOfFalseStringsSimpleName[i], argRegExprSimpleName, "SIMPLE_NAME_test_case");
                fail("Exception expected for: " + listOfFalseStringsSimpleName[i]);
            } catch (KapuaIllegalArgumentException e) {
                // Expected
            }
        }
        // Positive tests
        for (int i = 0; i < sizeOfPermittedStrings; i++) {
            try {
                ArgumentValidator.match(listOfPermittedStringsSimpleName[i], argRegExprSimpleName, "SIMPLE_NAME_test_case");
            } catch (Exception ex) {
                fail("No exception expected for: " + listOfPermittedStringsSimpleName[i]);
            }
        }
    }

    @Test
    public void testIllegalCharacterNameRegExp() throws Exception {
        String argRegExprName = "^[a-zA-Z0-9\\_\\-]{3,}$";
        String permittedSymbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890-_";
        String[] listOfFalseStringsName = new String[] { "abc!", "abc\\", "abc#", "abc$", "abc%", "abc&", "abc'", "abc(",
                "abc)", "abc=", "abc?", "abc*", "abc:", "abc;", "abc>", "abc<", "abc.", "abc,", "abc¡", "abc™", "abc£",
                "abc¢", "abc∞", "abc§", "abc¶", "abc•", "abcª", "abcº", "abc≠", "abcœ", "abc∑", "abc´", "abc®", "abc†", "abc—",
                "abc¨", "abc^", "abcø", "abcπ", "abc[", "abc]", "abcå", "abcß", "abc∂", "abcƒ", "abc©", "abc ", "̏abc", "abc∆", "abc¬",
                "abc…", "abc^", "abc\\", "abcΩ", "abc≈", "abcç", "abc√", "abc∫", "abc~", "abcµ", "abc≤", "abc≥", "abc÷", "abc⁄", "abc@",
                "abc‹", "abc›", "abc€", "abcı", "abc–", "abc°", "abc·", "abc‚", "abc±", "abcŒ", "abc„", "abc‘", "abc”", "abc’",
                "abcÉ", "abcØ", "abc∏", "abc{", "abc}", "abcÅ", "abcÍ", "abcÔ", "abc", "abcÒ", "abcæ", "abcÆ", "abc|", "abc«", "abc◊",
                "abcÑ", "abc¯", "abcÈ", "abcˇ", "abc¿", "", "a", "ab" };
        int sizeOfFalseStrings = listOfFalseStringsName.length;
        String[] listOfPermittedStringsName = new String[] { permittedSymbols, "aBc1-_", "acbd", "1234", "-_-_-" };
        int sizeOfPermittedStrings = listOfPermittedStringsName.length;
        // Negative tests
        for (int i = 0; i < sizeOfFalseStrings; i++) {
            try {
                ArgumentValidator.match(listOfFalseStringsName[i], argRegExprName, "NAME_REGEXP_test_case");
                fail("Exception expected for: " + listOfFalseStringsName[i]);
            } catch (KapuaIllegalArgumentException e) {
                // Expected
            }
        }
        // Positive tests
        for (int i = 0; i < sizeOfPermittedStrings; i++) {
            try {
                ArgumentValidator.match(listOfPermittedStringsName[i], argRegExprName, "NAME_REGEXP_test_case");
            } catch (Exception ex) {
                fail("No exception expected for: " + listOfPermittedStringsName[i]);
            }
        }
    }

    @Test
    public void testIllegalCharacterNameSpaceRegExp() throws Exception {
        String argRegExprNameSpace = "^[a-zA-Z0-9\\ \\_\\-]{3,}$";
        String permittedSymbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890-_ ";
        String[] listOfFalseStringsNameSpace = new String[] { "abc!", "abc\\", "abc#", "abc$", "abc%", "abc&", "abc'",
                "abc(", "abc)", "abc=", "abc?", "abc*", "abc:", "abc;", "abc>", "abc<", "abc.", "abc,", "abc¡", "abc™",
                "abc£", "abc¢", "abc∞", "abc§", "abc¶", "abc•", "abcª", "abcº", "abc≠", "abcœ", "abc∑", "abc´", "abc®", "abc†",
                "abc—", "abc¨", "abc^", "abcø", "abcπ", "abc[", "abc]", "abcå", "abcß", "abc∂", "abcƒ", "abc©", "abc ", "̏abc", "abc∆",
                "abc…", "abc^", "abc\\", "abcΩ", "abc≈", "abcç", "abc√", "abc∫", "abc~", "abcµ", "abc≤", "abc≥", "abc÷", "abc⁄", "abc@",
                "abc‹", "abc›", "abc€", "abcı", "abc–", "abc°", "abc·", "abc‚", "abc±", "abcŒ", "abc„", "abc‘", "abc”", "abc’",
                "abcÉ", "abcØ", "abc∏", "abc{", "abc}", "abcÅ", "abcÍ", "abcÔ", "abc", "abcÒ", "abcæ", "abcÆ", "abc|", "abc«", "abc◊",
                "abcÑ", "abc¯", "abcÈ", "abcˇ", "abc¿", "", "a", "ab", "abc¬", };
        int sizeOfFalseStrings = listOfFalseStringsNameSpace.length;
        String[] listOfPermittedStringsNameSpace = new String[] { permittedSymbols, "abc", "123", "ab1", "1ab", "ABC",
                "A1B", "A b", "A-ab1", "A_1", "ab-", "___", "---", "   ", "_- ", "ab ", "12 ", "12_", "2-1", "abcd1234-_ " };
        int sizeOfPermittedStrings = listOfPermittedStringsNameSpace.length;
        for (int i = 0; i < sizeOfFalseStrings; i++) {
            try {
                ArgumentValidator.match(listOfFalseStringsNameSpace[i], argRegExprNameSpace, "NAME_SPACE_REGEXP_test_case");
                fail("Exception expected for: " + listOfFalseStringsNameSpace[i]);
            } catch (KapuaIllegalArgumentException e) {
                // Expected
            }
        }
        for (int i = 0; i < sizeOfPermittedStrings; i++) {
            try {
                ArgumentValidator.match(listOfPermittedStringsNameSpace[i], argRegExprNameSpace, "NAME_SPACE_REGEXP_test_case");
            } catch (Exception ex) {
                fail("No exception expected for: " + listOfPermittedStringsNameSpace[i]);
            }
        }
    }

    @Test
    public void testIllegalCharacterTagNameRegExp() throws Exception {
        String argRegExprTagNameRegExp = "[A-Za-z0-9-_@#!$%^&*+=?<>]{3,255}";
        StringBuilder tagName = new StringBuilder();
        StringBuilder tagTooLong = new StringBuilder();
        Random random = new Random();
        String permittedSymbols = "abcdefghijklmnopqrstuvxwyzABCDEFGHIJKLMNOPQRSTUVXYZ1234567890-_@#!$%^&*+=?<>";
        for (int i = 0; i < 254; i++) {
            tagName.append(permittedSymbols.charAt(random.nextInt(permittedSymbols.length())));
        }
        for (int i = 0; i < 256; i++) {
            tagTooLong.append(permittedSymbols.charAt(random.nextInt(permittedSymbols.length())));
        }
        String tagNameOk = tagName.toString();
        String tagNameTooLong = tagTooLong.toString();
        String[] listOfFalseStringsTagNameRegExp = new String[] { "abc_ABC123'", "abc_ABC123(", "abc_ABC123)",
                "abc_ABC123;", "abc_ABC123:", "abc_ABC123,", "abc_ABC123.", "abc_ABC123¡", "abc_ABC123™", "abc_ABC123£", "abc_ABC123¢",
                "abc_ABC123∞", "abc_ABC123§", "abc_ABC123¶", "abc_ABC123•", "abc_ABC123º", "abc_ABC123≠", "abc_ABC123œ", "abc_ABC123∑",
                "abc_ABC123´", "abc_ABC123®", "abc_ABC123†", "abc_ABC123¨", "abc_ABC123ø", "abc_ABC123π", "abc_ABC123[", "abc_ABC123]",
                "abc_ABC123å", "abc_ABC123ß", "abc_ABC123∂", "abc_ABC123ƒ", "abc_ABC123©", "abc_ABC123 ̏", "abc_ABC123∆", "abc_ABC123¬",
                "abc_ABC123…", "abc_ABC123\"", "abc_ABC123`", "abc_ABC123Ω", "abc_ABC123≈", "abc_ABC123¿", "abc_ABC123ç", "abc_ABC123√",
                "abc_ABC123∫", "abc_ABC123~", "abc_ABC123µ", "abc_ABC123≤", "abc_ABC123≥", "abc_ABC123÷", "abc_ABC123⁄", "abc_ABC123‹",
                "abc_ABC123›", "abc_ABC123€", "abc_ABC123ı", "abc_ABC123–", "abc_ABC123°", "abc_ABC123±", "abc_ABC123Œ", "abc_ABC123„",
                "abc_ABC123‰", "abc_ABC123“", "abc_ABC123‘", "abc_ABC123 ", "abc_ABC123”", "abc_ABC123’", "abc_ABC123É", "abc_ABC123Ø",
                "abc_ABC123∏", "abc_ABC123{", "abc_ABC123}", "abc_ABC123Å", "abc_ABC123Í", "abc_ABC123Î", "abc_ABC123Ï", "abc_ABC123Ì",
                "abc_ABC123Ó", "abc_ABC123Ô", "abc_ABC123", "abc_ABC123Ò", "abc_ABC123æ", "abc_ABC123Æ", "abc_ABC123|", "abc_ABC123~",
                "abc_ABC123«", "abc_ABC123»", "abc_ABC123Ç", "abc_ABC123◊", "abc_ABC123Ñ", "abc_ABC123ˆ", "abc_ABC123¯", "abc_ABC123È",
                "abc_ABC123ˇ", tagNameTooLong };
        int sizeOfFalseStrings = listOfFalseStringsTagNameRegExp.length;
        String[] listOfPermittedStringTagNameRegExp = new String[] { tagNameOk, "abc_ABC123-", "abc_ABC123_", "abc_ABC123@", "abc_ABC123#",
                "abc_ABC123!", "abc_ABC123$", "abc_ABC123%", "abc_ABC123^", "abc_ABC123&", "abc_ABC123*", "abc_ABC123+", "abc_ABC123=", "abc_ABC123?",
                "abc_ABC123<", "abc_ABC123>", "abc", "ABC", "123", "abc_ABC123", "%&$&!", "&av231j", "123" };
        int sizeOfPermittedStrings = listOfPermittedStringTagNameRegExp.length;
        for (int i = 0; i < sizeOfFalseStrings; i++) {
            try {
                ArgumentValidator.match(listOfFalseStringsTagNameRegExp[i], argRegExprTagNameRegExp, "TAGE_NAME_test_case");
                fail("Exception expected for: " + listOfFalseStringsTagNameRegExp[i]);
            } catch (KapuaIllegalArgumentException ex) {
                // Expected
            }
        }
        for (int i = 0; i < sizeOfPermittedStrings; i++) {
            try {
                ArgumentValidator.match(listOfPermittedStringTagNameRegExp[i], argRegExprTagNameRegExp, "TAGE_NAME_test_case");
            } catch (Exception ex) {
                fail("No exception expected for: " + listOfPermittedStringTagNameRegExp[i]);
            }
        }
    }

    @Test
    public void testIllegalCharacterMacAddressRegExp() throws Exception {
        String argRegExprMACaddress = "^([0-9A-F]{2}[:-]){5}([0-9A-F]{2})$";
        String[] listOfFalseStringsMACaddressRegExp = new String[] { "00:11:22:33:44", "00:11:22:33:44:55:66",
                "00-11-22-33-44", "00-11-22-33-44-55-66", "00:11:22:33:44:55:", "00-11-22-33-44-55-", "0:11:22:33:44:55", "00:1:22:33:44:55",
                "00:11:2:33:44:55", "00:11:22:3:44:55", "00:11:22:33:4:55", "00:11:22:33:44:5", "0-11-22-33-44-55", "00-1-22-33-44-55",
                "00-11-2-33-44-55", "00-11-22-3-44-55", "00-11-22-33-4-55", "00-11-22-33-44-5", "01:23:45:6:78:90", "34:56:78:90:A:BC",
                "000:11:22:33:44:55", "00:111:AB:CD:EF:34", "00:11:ABC:DE:F1:34", "00:11:AB:CDE:F1:34", "00:11:AB:CD:EF1:34", "00:11:AB:CD:EF1:34",
                "00:11:AB:CD:EF:345", "000-11-AB-CD-EF-34", "00-111-AB-CD-EF-34", "00-11-ABC-DE-F1-34", "00-AA-AB-CDE-F1-34", "00-11-AB-CD-EF1-34",
                "00-11-AB-CD-EF-345", "AB:CD:eF.AB.CD.EF", "56:78:90:aB:CD:EF", "12:34:56:78:G1:12", "H1:00:12:23:AB:CD", "12:34-56-:78:AB:CD",
                ":00:11:22:33:44:55", "-00-11-22-33-44-55", "12-34-56-78_90-AB", "12.34.56.78.90.AB", "AV:CD:EF:12:34:56", "AB:CS:EF-12-34-45" };
        int sizeOfFalseStringsMACaddress = listOfFalseStringsMACaddressRegExp.length;
        String[] listOfPermittedStringsMACaddressRegExp = new String[] { "00:11:22:33:44:55", "00-11-22-33-44-55", "0A:BC:DE:12:34:56",
                "0A-BC-DE-12-34-56", "01:23:45:67:89:AB", "00:00:00:00:00:00", "FF:FF:FF:FF:FF:FF", "01-23:45-67:89:A0", "00:13:25:AF:AF:AF",
                "00-11-22-33-44-55", "0A-2B-3C-4D-5F-1E" };
        int sizeOfPermittedStringsMACaddress = listOfPermittedStringsMACaddressRegExp.length;
        for (int i = 0; i < sizeOfFalseStringsMACaddress; i++) {
            try {
                ArgumentValidator.match(listOfFalseStringsMACaddressRegExp[i], argRegExprMACaddress, "MAC_ADDRESS_test_case");
                fail("Exception expected for: " + listOfFalseStringsMACaddressRegExp[i]);
            } catch (KapuaIllegalArgumentException ex) {
                // Expected
            }
        }
        for (int i = 0; i < sizeOfPermittedStringsMACaddress; i++) {
            try {
                ArgumentValidator.match(listOfPermittedStringsMACaddressRegExp[i], argRegExprMACaddress, "MAC_ADDRESS_test_case");
            } catch (Exception ex) {
                fail("No exception expected for: " + listOfPermittedStringsMACaddressRegExp[i]);
            }
        }
    }

    @Test
    public void testIllegalCharacterPasswordRegExp() throws Exception {
        String argRegExprPasswordRegExp = "^.*(?=.{12,})(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!\\~\\|]).*$";
        String permittedSymbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890-_@#$%^&+=!~|";
        String[] listOfFalseStringsPasswordRegExp = new String[] { "abcd1234ABCD\"", "abcd1234ABCD?", "abcd1234ABCD*",
                "abcd1234ABCD;", "abcd1234ABCD:", "abcd1234ABCD<", "abcd1234ABCD>", "abcd1234ABCD[", "abcd1234ABCD]",
                "abcd1234ABCD{", "abcd1234ABCD}", "abcd1234ABCD⁄", "abcd1234ABCD€", "abcd1234ABCD(", "abcd1234ABCD)",
                "abcd1234ABCD.", "abcd1234ABCD,", "abcd1234ABCD¡", "abcd1234ABCD™", "abcd1234ABCD£", "abcd1234ABCD¢",
                "abcd1234ABCD∞", "abcd1234ABCD§", "abcd1234ABCD¶", "abcd1234ABCD•", "abcd1234ABCDª", "abcd1234ABCDº",
                "abcd1234ABCD-", "abcd1234ABCD≠", "abcd1234ABCD ", "abcd1234ABCD∑", "abcd1234ABCD´", "abcd1234ABCD®",
                "abcd1234ABCD†", "abcd1234ABCD—", "abcd1234ABCD¨", "abcd1234ABCDø", "abcd1234ABCDπ", "abcd1234ABCD-",
                "abcd1234ABCDå", "abcd1234ABCDß", "abcd1234ABCD∂", "abcd1234ABCD", "abcd1234ABCD©", "abcdefghijklm¿",
                "abcd1234ABCD∆", "abcd1234ABCD¬", "abcd1234ABCD…", "abcd1234ABCD\\", "abcd1234ABCDΩ", "ABCDefghij12_",
                "abcd1234ABCD≈", "abcd1234ABCDç", "abcd1234ABCD√", "abcd1234ABCD∫", "abcd1234ABCDµ", "abcd1234ABCD≤",
                "abcd1234ABCD≥", "abcd1234ABCD÷", "abcd1234ABCD‹", "abcd1234ABCD›", "abcd1234ABCD€", "abcd1234ABCDı",
                "abcd1234ABCD°", "abcd1234ABCD·", "abcd1234ABCD‚", "abcd1234ABCD±", "abcd1234ABCDŒ", "abcd1234ABCD„",
                "abcd1234ABCD‘", "abcd1234ABCD”", "abcd1234ABCD’", "abcd1234ABCDÉ", "abcd1234ABCDØ", "abcd1234ABCD∏",
                "abcd1234ABCDÅ", "abcd1234ABCDÍ", "abcd1234ABCDÔ", "abcd1234ABCD", "abcd1234ABCDÒ", "abcd1234ABCDæ",
                "abcd1234ABCDÆ", "abcd1234ABCD«", "abcd1234ABCD◊", "abcd1234ABCDÑ", "abcd1234ABCD¯",
                "abcd1234ABCDÈ", "abcd1234ABCDˇ" };
        int sizeOfFalseStrings = listOfFalseStringsPasswordRegExp.length;
        String[] listOfPermittedStringsPasswordRegExp = new String[] { permittedSymbols, "ABCDefghij12@", "ABCDEfghij12#",
                "ABCDEfghij12$", "ABCDEfghij12%", "ABCDEfghij12^", "ABCDEfghij12&", "ABCDEfghij12+", "ABCDEfghij12=",
                "ABCDEfghij12!", "ABCDEfghij12~", "ABCDEfghij12|", "ABCDEfghij1!", "ab12CD23!&)$%!" };
        int sizeOfPermittedStrings = listOfPermittedStringsPasswordRegExp.length;
        for (int i = 0; i < sizeOfFalseStrings; i++) {
            try {
                ArgumentValidator.match(listOfFalseStringsPasswordRegExp[i], argRegExprPasswordRegExp, "PASSWORD_REGEXP_test_case");
                fail("Exception expected for: " + listOfFalseStringsPasswordRegExp[i]);
            } catch (KapuaIllegalArgumentException ex) {
                // Expected
            }
        }
        for (int i = 0; i < sizeOfPermittedStrings; i++) {
            try {
                ArgumentValidator.match(listOfPermittedStringsPasswordRegExp[i], argRegExprPasswordRegExp, "PASSWORD_REGEXP_test_case");
            } catch (Exception ex) {
                fail("No exception expected for: " + listOfPermittedStringsPasswordRegExp[i]);
            }
        }
    }

    @Test
    public void testIllegalCharacterEmailRegExp() throws Exception {
        String argRegExprEmailRegExp = "^(\\w+)([-+.][\\w]+)*@(\\w[-\\w]*\\.){1,5}([A-Za-z]){2,4}$";
        StringBuilder namePart = new StringBuilder();
        Random random = new Random();
        String permittedSymbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890";
        for (int i = 0; i < 500; i++) {
            namePart.append(permittedSymbols.charAt(random.nextInt(permittedSymbols.length())));
        }
        String longname = namePart.toString();
        String[] listOfFalseStringsEmailRegExp = new String[] { "abc!.ABC_123@co.co", "abc\".ABC_123@co.co",
                "abc#.ABC_123@co.co", "abc$.ABC_123@co.co", "abc%.ABC_123@co.co", "abc&.ABC_123@co.co", "abc'.ABC_123@co.co",
                "abc(.ABC_123@co.co", "abc).ABC_123@co.co", "abc=.ABC_123@co.co", "abc?.ABC_123@co.co", "abc*.ABC_123@co.co",
                "abc:.ABC_123@co.co", "abc;.ABC_123@co.co", "abc¶.ABC_123@co.co", "abc•.ABC_123@co.co", "abcº.ABC_123@co.co",
                "abc<.ABC_123@co.co", "abc>.ABC_123@co.co", "abc¡.ABC_123@co.co", "abc™.ABC_123@co.co", "abc[.ABC_123@co.co",
                "abc].ABC_123@co.co", "abc{.ABC_123@co.co", "abc}.ABC_123@co.co", "abc^.ABC_123@co.co", "abcÈ.ABC_123@co.co",
                "abc~.ABC_123@co.co", "abc±.ABC_123@co.co", "abc=.ABC_123@co.co", "abc£.ABC_123@co.co", "abc¢.ABC_123@co.co",
                "abc∞.ABC_123@co.co", "abc≤.ABC_123@co.co", "abc≥.ABC_123@co.co", "abc§.ABC_123@co.co", "abc•.ABC_123@co.co",
                "abc.ABC_123@co.co", "abcÒ.ABC_123@co.co", "abc«.ABC_123@co.co", "abc◊.ABC_123@co.co", "abcÑ.ABC_123@co.co",
                "abcˆ.ABC_123@co.co", "abc¯.ABC_123@co.co", "abcÉ.ABC_123@co.co", "abcØ.ABC_123@co.co", "abc∏.ABC_123@co.co",
                "abcÅ.ABC_123@co.co", "abcÍ.ABC_123@co.co", "abcÔ.ABC_123@co.co", "abc¿.ABC_123@co.co", "abcŒ.ABC_123@co.co",
                "abc\\.ABC_123@co.co", "abc„.ABC_123@co.co", "abc‘.ABC_123@co.co", "abc’.ABC_123@co.co", "abc‚.ABC_123@co.co",
                "abcæ.ABC_123@co.co", "abcÆ.ABC_123@co.co", "abc|.ABC_123@co.co", "abcÈ.ABC_123@co.co", "abcˇ.ABC_123@co.co",
                "abc‹.ABC_123@co.co", "abc›.ABC_123@co.co", "abc€.ABC_123@co.co", "abcı.ABC_123@co.co", "abc–.ABC_123@co.co",
                "abc°.ABC_123@co.co", "abc·.ABC_123@co.co", "abc.ABC_123@a.B._.1.-.C.co", "abc@to.12.TO._-.ab.to.co",
                "abc@com", "abc@co.c", "@co.co", "@com.com", "ab.@co.co", "abc.ABC_123@abc.toolong" };
        int sizeOfFalseStrings = listOfFalseStringsEmailRegExp.length;
        String[] listOfPermittedStringsEmailRegExp = new String[] { "a@co.co", "ab@co.co", "abc@co.co", "a.a@co.co", "ab.ab@co.co",
                "abc.abc@co.co", "1@co.co", "12@co.co", "123@co.co", "1.1@co.co", "12.12@co.co", "123.123@co.co", "a.1@co.co",
                "abc.123@co.co", "abc.ABC-123+_@co.co", "ab.AB+12-_@12.co", "ab.AB+12-_@co12.co", "ab.AB+12-_@1.co", "ab.AB+12-_@12.co",
                "ab.AB+12-_@123.co", "ab.AB+12-_@aB123.co", "ab.AB+12-_@ab-12.co", "abc123ABC@ABcde.coms", "aA1-_.aA1-_@a.A.1._1aB.co",
                longname + "@co.co", "a.a.a.a.a.a.a.a.a.a@co.co", "abc.ABC_123@A12bc_.ab.12c.1234abc_.co", "abc.ABC_123@a.ab.co", };
        int sizeOPermittedStrings = listOfPermittedStringsEmailRegExp.length;
        for (int i = 0; i < sizeOfFalseStrings; i++) {
            try {
                ArgumentValidator.match(listOfFalseStringsEmailRegExp[i], argRegExprEmailRegExp, "EMAIL_test_case");
                fail("Exception expected for: " + listOfFalseStringsEmailRegExp[i]);
            } catch (KapuaIllegalArgumentException ex) {
                // Expected
            }
        }
        for (int i = 0; i < sizeOPermittedStrings; i++) {
            try {
                ArgumentValidator.match(listOfPermittedStringsEmailRegExp[i], argRegExprEmailRegExp, "EMAIL_test_case");
            } catch (Exception ex) {
                fail("No exception expected for: " + listOfPermittedStringsEmailRegExp[i]);
            }
        }
    }

    @Test
    public void testNotNull() throws Exception {
        byte byteVariable = 0;
        short shortVariable = 12;
        int intVariable = 123456;
        String stringVariable = "string_variable";
        char charVariable = 'a';
        long longVariable = 1234567890;
        double doubleVariable = 1234567.1234567;
        float floatVariable = (float) 123.123457;
        Object[] listOfFalseStringsNullTest = new Object[] { null };
        int sizeOfFalseStringsNullTest = listOfFalseStringsNullTest.length;
        Object[] listOfPermittedStringNullTest = new Object[]{byteVariable, shortVariable, intVariable, charVariable,
                stringVariable, longVariable, doubleVariable, floatVariable};
        int sizeOfPermittedStrings = listOfPermittedStringNullTest.length;
        for (int i = 0; i < sizeOfFalseStringsNullTest; i++) {
            try {
                ArgumentValidator.notNull(listOfFalseStringsNullTest[i], "NULL_test_case");
                fail("Exception expeected for: " + listOfFalseStringsNullTest[i]);
            } catch (KapuaIllegalNullArgumentException ex) {
                // Expected
            }
        }
        for (int i = 0; i < sizeOfPermittedStrings; i++) {
            try {
                ArgumentValidator.notNull(listOfPermittedStringNullTest[i], "NULL_test_case");
            } catch (Exception ex) {
                fail("No exception expected for: " + listOfPermittedStringNullTest[i]);
            }
        }
    }

    @Test
    public void testIsNull() throws Exception {
        byte byteVariable = 0;
        short shortVariable = 12;
        int intVariable = 123456;
        String stringVariable = "string_variable";
        char charVariable = 'a';
        long longVariable = 1234567890;
        double doubleVariable = 1234567.1234567;
        float floatVariable = (float) 123.123457;
        Object[] listOfFalseStringsNullTest = new Object[] {byteVariable, shortVariable, intVariable, charVariable,
                stringVariable, longVariable, doubleVariable, floatVariable};
        int sizeOfFalseStringsNullTest = listOfFalseStringsNullTest.length;
        Object[] listOfPermittedStringNullTest = new Object[] {null};
        int sizeOfPermittedStrings = listOfPermittedStringNullTest.length;
        for (int i = 0; i < sizeOfFalseStringsNullTest; i++) {
            try {
                ArgumentValidator.isNull(listOfFalseStringsNullTest[i], "NULL_test_case");
                fail("Exception expeected for: " + listOfFalseStringsNullTest[i]);
            } catch (KapuaIllegalArgumentException ex) {
                // Expected
            }
        }
        for (int i = 0; i < sizeOfPermittedStrings; i++) {
            try {
                ArgumentValidator.isNull(listOfPermittedStringNullTest[i], "NULL_test_case");
            } catch (Exception ex) {
                fail("No exception expected for: " + listOfPermittedStringNullTest[i]);
            }
        }
    }

    @Test
    public void testIsEmptyOrNull() throws Exception {
        Random random = new Random();
        String permittedSymbols = "abcdefghijklmnopqrstuvxwyzABCDEFGHIJKLMNOPQRSTUVXYZ1234567890-_@#!$%^&*+=?<>";
        StringBuilder longStr = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            longStr.append(permittedSymbols.charAt(random.nextInt(permittedSymbols.length())));
        }
        String longString = longStr.toString();
        String[] listOfFalseStringsEmptyOrNull = new String[]{longString,"s","str","string","string"};
        int sizeOfFalseStringsEmptyOrNull = listOfFalseStringsEmptyOrNull.length;
        String[] listOfPermittedStringsEmptyOrNull = new String[] {null,""};
        int sizeOfPermittedStringsEmptyOrNull = listOfPermittedStringsEmptyOrNull.length;
        for (int i = 0; i < sizeOfFalseStringsEmptyOrNull; i++) {
            try {
                ArgumentValidator.isEmptyOrNull(listOfFalseStringsEmptyOrNull[i], "EMPTY_OR_NULL_test_case");
                fail("Exception expeected for: " + listOfFalseStringsEmptyOrNull[i]);
            } catch (KapuaIllegalArgumentException ex) {
                // Expected
            }
        }
        for (int i = 0; i < sizeOfPermittedStringsEmptyOrNull; i++) {
            try {
                ArgumentValidator.isEmptyOrNull(listOfPermittedStringsEmptyOrNull[i], "EMPTY_OR_NULL_test_case");
            } catch (Exception ex) {
                fail("No exception expected for: " + listOfPermittedStringsEmptyOrNull[i]);
            }
        }
    }

    @Test
    public void testNotEmptyOrNullString() throws Exception {
        String[] listOfFalseStrings = new String[] { "", null };
        int sizeOfFalseStrings = listOfFalseStrings.length;
        String[] listOfPermittedStrings = new String[]{"a","ab","abc","string"};
        int sizeOfPermittedStrings = listOfPermittedStrings.length;
        for (int i = 0; i < sizeOfFalseStrings; i++) {
            try {
                ArgumentValidator.notEmptyOrNull(listOfFalseStrings[i], "notEmptyOrNullTest");
                fail("Exception expected for: " + listOfFalseStrings[i]);
            } catch (Exception ex) {
                // Expected
            }
        }

        for (int i = 0; i < sizeOfPermittedStrings; i++) {
            try {
                ArgumentValidator.notEmptyOrNull(listOfPermittedStrings[i], "notEmptyOrNullTest");
            } catch (Exception ex) {
                fail("No Exception expected for: " + listOfPermittedStrings[i]);
            }
        }
    }

    @Test
    public void testNotNegative() throws KapuaIllegalNullArgumentException {
        long[] listOfNegative = new long[] {-1,-12,-123,-1234,-12345,-123456,-1234567,
                -12345678};
        int sizeOfNegative = listOfNegative.length;
        long[] listOfPositive = new long[]{0,1,12,123,1234,123456,1234567,12345678};
        int sizeOfPositive = listOfPositive.length;
        for (int i = 0; i < sizeOfNegative; i++) {
            try {
                ArgumentValidator.notNegative(listOfNegative[i], "not null test");
                fail("Exception expected for : " + listOfNegative[i]);
            } catch (Exception ex) {
                // Expected
            }
        }
        for (int i = 0; i < sizeOfPositive; i++) {
            try {
                ArgumentValidator.notNegative(listOfPositive[i], "not null test");
            } catch (Exception ex) {
                fail("No exception expected for: " + listOfPositive[i]);
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testDateRange() throws Exception {

        Date startTimeFalse2 = new Date(2018, 1, 1);
        Date endTimeFalse2 = new Date(2017, 1, 1);

        Date[] startTimeFalse = new Date[] { startTimeFalse2 };
        Date[] endTimeFalse = new Date[] { endTimeFalse2 };

        Date startTimeOK0 = new Date(2017, 1, 1);
        Date endTimeOK0 = new Date(2018, 1, 1);

        Date startTimeOK1 = new Date(-2017, -10, -10);
        Date endTimeOK1 = new Date(2018, 1, 1);

        @SuppressWarnings("unused")
        Date startTimeOK2 = new Date(2017, 1, 1);
        Date endTimeOK2 = new Date(2018, -1, -1);

        Date[] startTimeOK = new Date[] { startTimeOK0, startTimeOK1, startTimeOK1 };
        Date[] endTimeOK = new Date[] { endTimeOK0, endTimeOK1, endTimeOK2 };

        for (int i = 0; i < startTimeFalse.length; i++) {
            try {
                ArgumentValidator.dateRange(startTimeFalse[i], endTimeFalse[i]);
                fail("Exception expected for: " + i);
            } catch (Exception ex) {
                // Expected
            }
        }
        for (int i = 0; i < startTimeOK.length; i++) {
            try {
                ArgumentValidator.dateRange(startTimeOK[i], endTimeOK[i]);
            } catch (Exception ex) {
                fail("No exception expected for: " + startTimeOK[i] + endTimeOK[i]);
            }
        }
    }

    @Test
    public void testDateRangeLong() throws Exception {

        long startTimeOK0 = 123456789L;
        long endTimeOK0 = 1234567899L;

        long startTimeOK1 = 123456789L;
        long endTimeOK1 = 123456789L;

        long startTimeOK2 = -1L;
        long endTimeOK2 = -1L;

        long startTimeOK3 = 123456L;
        long endTimeOK3 = -1L;

        long startTimeOK4 = -1L;
        long endTimeOK4 = 12341231411L;

        long[] startTimeOK = new long[] { startTimeOK0, startTimeOK1,startTimeOK2,
                startTimeOK3,startTimeOK4};
        long[] endTimeOK = new long[] { endTimeOK0, endTimeOK1,endTimeOK2,
                endTimeOK3,endTimeOK4};

        long startTimeNOK0 = 1234567890123456789L;
        long endTimeNOK0 = 123456789012345L;

        long[] startTimeNOK = new long[] {startTimeNOK0};
        long[] endTimeNOK = new long[] {endTimeNOK0};

        for (int i = 0; i < startTimeNOK.length; i++) {
            try {
                ArgumentValidator.dateRange(startTimeNOK[i], endTimeNOK[i]);
                fail("Exception expected for LONG: " + i);
            } catch (Exception ex) {
                // Expected
            }
        }
        for (int i = 0; i < startTimeOK.length; i++) {
            try {
                ArgumentValidator.dateRange(startTimeOK[i], endTimeOK[i]);
            } catch (Exception ex) {
                fail("No exception expected for: " + startTimeOK[i] + endTimeOK[i]);
            }
        }
    }

    @Test
    public void testNumRange() throws Exception {
        long minValue = 12;
        long maxValue = 12345;
        long numRangeFalse[] = new long[] { 0, 11, 12346 };
        long numRangePermitted[] = new long[] { 12, 15, 12345 };
        for (long element : numRangeFalse) {
            try {
                ArgumentValidator.numRange(element, minValue, maxValue, "numRange test");
                fail("Exception expected for: " + element);
            } catch (Exception ex) {
                // Expected
            }
        }
        for (long element : numRangePermitted) {
            try {
                ArgumentValidator.numRange(element, minValue, maxValue, "numRange test");
            } catch (Exception ex) {
                fail("No exception expected for: " + element);
            }
        }
    }
}