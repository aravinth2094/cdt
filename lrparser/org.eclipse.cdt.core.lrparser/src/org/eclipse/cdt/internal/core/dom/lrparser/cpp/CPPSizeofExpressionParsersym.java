/*******************************************************************************
* Copyright (c) 2006, 2010 IBM Corporation and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*     IBM Corporation - initial API and implementation
*********************************************************************************/

// This file was generated by LPG

package org.eclipse.cdt.internal.core.dom.lrparser.cpp;

public interface CPPSizeofExpressionParsersym {
    public final static int
      TK_asm = 64,
      TK_auto = 28,
      TK_bool = 11,
      TK_break = 76,
      TK_case = 77,
      TK_catch = 102,
      TK_char = 12,
      TK_class = 40,
      TK_const = 23,
      TK_const_cast = 42,
      TK_continue = 78,
      TK_default = 79,
      TK_delete = 65,
      TK_do = 80,
      TK_double = 13,
      TK_dynamic_cast = 43,
      TK_else = 121,
      TK_enum = 56,
      TK_explicit = 29,
      TK_export = 86,
      TK_extern = 30,
      TK_false = 44,
      TK_float = 14,
      TK_for = 81,
      TK_friend = 31,
      TK_goto = 82,
      TK_if = 83,
      TK_inline = 32,
      TK_int = 15,
      TK_long = 16,
      TK_mutable = 33,
      TK_namespace = 59,
      TK_new = 66,
      TK_operator = 7,
      TK_private = 103,
      TK_protected = 104,
      TK_public = 105,
      TK_register = 34,
      TK_reinterpret_cast = 45,
      TK_return = 84,
      TK_short = 17,
      TK_signed = 18,
      TK_sizeof = 46,
      TK_static = 35,
      TK_static_cast = 47,
      TK_struct = 57,
      TK_switch = 85,
      TK_template = 48,
      TK_this = 49,
      TK_throw = 60,
      TK_try = 74,
      TK_true = 50,
      TK_typedef = 36,
      TK_typeid = 51,
      TK_typename = 10,
      TK_union = 58,
      TK_unsigned = 19,
      TK_using = 62,
      TK_virtual = 22,
      TK_void = 20,
      TK_volatile = 24,
      TK_wchar_t = 21,
      TK_while = 75,
      TK_integer = 52,
      TK_floating = 53,
      TK_charconst = 54,
      TK_stringlit = 39,
      TK_identifier = 1,
      TK_Completion = 2,
      TK_EndOfCompletion = 8,
      TK_Invalid = 122,
      TK_LeftBracket = 63,
      TK_LeftParen = 3,
      TK_Dot = 119,
      TK_DotStar = 91,
      TK_Arrow = 106,
      TK_ArrowStar = 89,
      TK_PlusPlus = 37,
      TK_MinusMinus = 38,
      TK_And = 9,
      TK_Star = 6,
      TK_Plus = 25,
      TK_Minus = 26,
      TK_Tilde = 5,
      TK_Bang = 41,
      TK_Slash = 92,
      TK_Percent = 93,
      TK_RightShift = 87,
      TK_LeftShift = 88,
      TK_LT = 55,
      TK_GT = 68,
      TK_LE = 94,
      TK_GE = 95,
      TK_EQ = 96,
      TK_NE = 97,
      TK_Caret = 98,
      TK_Or = 99,
      TK_AndAnd = 100,
      TK_OrOr = 101,
      TK_Question = 107,
      TK_Colon = 72,
      TK_ColonColon = 4,
      TK_DotDotDot = 90,
      TK_Assign = 70,
      TK_StarAssign = 108,
      TK_SlashAssign = 109,
      TK_PercentAssign = 110,
      TK_PlusAssign = 111,
      TK_MinusAssign = 112,
      TK_RightShiftAssign = 113,
      TK_LeftShiftAssign = 114,
      TK_AndAssign = 115,
      TK_CaretAssign = 116,
      TK_OrAssign = 117,
      TK_Comma = 69,
      TK_RightBracket = 118,
      TK_RightParen = 73,
      TK_RightBrace = 71,
      TK_SemiColon = 27,
      TK_LeftBrace = 67,
      TK_ERROR_TOKEN = 61,
      TK_EOF_TOKEN = 120;

      public final static String orderedTerminalSymbols[] = {
                 "",
                 "identifier",
                 "Completion",
                 "LeftParen",
                 "ColonColon",
                 "Tilde",
                 "Star",
                 "operator",
                 "EndOfCompletion",
                 "And",
                 "typename",
                 "bool",
                 "char",
                 "double",
                 "float",
                 "int",
                 "long",
                 "short",
                 "signed",
                 "unsigned",
                 "void",
                 "wchar_t",
                 "virtual",
                 "const",
                 "volatile",
                 "Plus",
                 "Minus",
                 "SemiColon",
                 "auto",
                 "explicit",
                 "extern",
                 "friend",
                 "inline",
                 "mutable",
                 "register",
                 "static",
                 "typedef",
                 "PlusPlus",
                 "MinusMinus",
                 "stringlit",
                 "class",
                 "Bang",
                 "const_cast",
                 "dynamic_cast",
                 "false",
                 "reinterpret_cast",
                 "sizeof",
                 "static_cast",
                 "template",
                 "this",
                 "true",
                 "typeid",
                 "integer",
                 "floating",
                 "charconst",
                 "LT",
                 "enum",
                 "struct",
                 "union",
                 "namespace",
                 "throw",
                 "ERROR_TOKEN",
                 "using",
                 "LeftBracket",
                 "asm",
                 "delete",
                 "new",
                 "LeftBrace",
                 "GT",
                 "Comma",
                 "Assign",
                 "RightBrace",
                 "Colon",
                 "RightParen",
                 "try",
                 "while",
                 "break",
                 "case",
                 "continue",
                 "default",
                 "do",
                 "for",
                 "goto",
                 "if",
                 "return",
                 "switch",
                 "export",
                 "RightShift",
                 "LeftShift",
                 "ArrowStar",
                 "DotDotDot",
                 "DotStar",
                 "Slash",
                 "Percent",
                 "LE",
                 "GE",
                 "EQ",
                 "NE",
                 "Caret",
                 "Or",
                 "AndAnd",
                 "OrOr",
                 "catch",
                 "private",
                 "protected",
                 "public",
                 "Arrow",
                 "Question",
                 "StarAssign",
                 "SlashAssign",
                 "PercentAssign",
                 "PlusAssign",
                 "MinusAssign",
                 "RightShiftAssign",
                 "LeftShiftAssign",
                 "AndAssign",
                 "CaretAssign",
                 "OrAssign",
                 "RightBracket",
                 "Dot",
                 "EOF_TOKEN",
                 "else",
                 "Invalid"
             };

    public final static boolean isValidForParser = true;
}
