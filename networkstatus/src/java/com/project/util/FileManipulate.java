/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.util;

/**
 *
 * @author Samrit
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class FileManipulate {

    private BufferedReader br = null;
    private List<String> filecontent;
    private int fileline = 0;
    private int currentline = 0;
    private RegEx_Tool regex_tool = new RegEx_Tool();

    public int LoadAll(String paramString) {
        try {
            this.fileline = 0;
            this.currentline = 0;
            this.filecontent = new ArrayList();
            this.filecontent.add(0, "");
            this.br = new BufferedReader(new FileReader(paramString));
            String str = "";
            while ((str = this.br.readLine()) != null) {
                this.fileline += 1;
                this.currentline += 1;
                this.filecontent.add(this.fileline, str);
            }
            this.br.close();
        } catch (Exception localException) {
            return 1;
        }
        return 0;
    }

    public int GetSize() {
        return this.fileline;
    }

    public int GetCurrent() {
        return this.currentline;
    }

    public int SetCurrent(int paramInt) {
        if (paramInt < 1) {
            return this.currentline;
        }
        if (paramInt > this.fileline) {
            return this.currentline;
        }
        this.currentline = paramInt;
        return this.currentline;
    }

    public String Get1LineStr(int paramInt) {
        if ((paramInt >= 1) && (paramInt <= this.fileline)) {
            return (String) this.filecontent.get(paramInt);
        }
        return null;
    }

    public String Getmatched(String paramString) {
        for (int i = 1; i <= this.fileline; i++) {
            String str = this.regex_tool.capture(paramString, (String) this.filecontent.get(i));
            if (str == null) {
                continue;
            }
            this.currentline = i;
            return str;
        }
        this.currentline = 0;
        return null;
    }

    public String Getmatched(String paramString, int paramInt) {
        if (paramInt < 1) {
            return null;
        }
        if (paramInt > this.fileline) {
            return null;
        }
        while (paramInt <= this.fileline) {
            String str = this.regex_tool.capture(paramString, (String) this.filecontent.get(paramInt));
            if (str != null) {
                this.currentline = paramInt;
                return str;
            }
            paramInt++;
        }
        this.currentline = 0;
        return null;
    }

    public String Getmatched_text(String paramString, int paramInt) {
        if (paramInt < 1) {
            return null;
        }
        if (paramInt > this.fileline) {
            return null;
        }

        String str = this.regex_tool.capture(paramString, (String) this.filecontent.get(paramInt));
        return str;

    }

    public int CreateNewFile(String paramString) {
        try {
            FileWriter localFileWriter = new FileWriter(paramString);
            BufferedWriter localBufferedWriter = new BufferedWriter(localFileWriter);
            localBufferedWriter.close();
        } catch (Exception localException) {
            return 1;
        }
        return 0;
    }

    public int WriteAll(String paramString) {
        try {
            FileWriter localFileWriter = new FileWriter(paramString);
            BufferedWriter localBufferedWriter = new BufferedWriter(localFileWriter);
            for (int i = 1; i <= this.fileline; i++) {
                localBufferedWriter.write(Get1LineStr(i));
                localBufferedWriter.newLine();
            }
            localBufferedWriter.close();
        } catch (Exception localException) {
            return 1;
        }
        return 0;
    }

    public int UpdateList(String paramString1, String paramString2, String paramString3, String paramString4) {
        return UpdateList(paramString1, paramString2, paramString3, paramString4, 1);
    }

    public int UpdateList(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt) {
        String str1;
        if (paramInt <= this.fileline) {
            str1 = Getmatched(paramString1, paramInt);
        } else {
            str1 = null;
        }
        String str2;
        if (str1 != null) {
            int i = GetCurrent();
            str2 = this.regex_tool.replace(paramString2, Get1LineStr(i), paramString3);
            this.filecontent.set(i, str2);
            return 0;
        }
        if (str1 == null) {
            str2 = this.regex_tool.replace(paramString2, paramString4, paramString3);
            Append(str2);
            this.currentline += 1;
        }
        return 1;
    }

    public int Remove() {
        this.filecontent.remove(this.currentline);
        this.currentline -= 1;
        this.fileline -= 1;
        return 0;
    }

    public int Replace(String paramString) {
        int i = GetCurrent();
        this.filecontent.set(i, paramString);
        return 0;
    }

    public int Append(String paramString) {
        this.fileline += 1;
        this.filecontent.add(this.fileline, paramString);
        return 0;
    }

    public int Insert(String paramString, int paramInt) {
        if (paramInt < 1) {
            return -1;
        }
        if (paramInt > this.fileline + 1) {
            return -1;
        }
        this.fileline += 1;
        this.filecontent.add(paramInt, paramString);
        return 0;
    }

    public boolean isMatched(String paramString1) {
        for (int i = 1; i <= this.fileline; i++) {
            boolean linecheck = this.filecontent.get(i).trim().contains(paramString1);
            if (linecheck == false) {
                continue;
            }
            this.currentline = i;
            return true;
        }
        this.currentline = 0;
        return false;
    }


    public int UpdateListReplaceOnly(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt) {
        String str1;
        if (paramInt <= this.fileline) {
            str1 = Getmatched(paramString1, paramInt);
        } else {
            str1 = null;
        }
        String str2;
        if (str1 != null) {
            int i = GetCurrent();
            str2 = this.regex_tool.replace(paramString2, Get1LineStr(i), paramString3);
            this.filecontent.set(i, str2);
            return 0;
        }

        return 1;
    }

}
