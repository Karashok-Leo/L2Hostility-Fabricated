package net.karashokleo.l2hostility.util;

public class StringUtil
{
    public static String getNameById(String id)
    {
        String[] words = id.split("_");
        StringBuilder sb = new StringBuilder();
        for (String word : words)
            if (!word.isEmpty())
                sb.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
        return sb.toString().trim();
    }
}
