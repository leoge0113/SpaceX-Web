package com.cainiao.util;


import java.util.ArrayList;
import java.util.List;

public class ProtoStuffTest {
    Object C;

    public Object getC() {
        return C;
    }

    public void setC(Object c) {
        C = c;
    }

    public static void main(String[] args) {
        B b = new B();
        ProtoStuffTest a = new ProtoStuffTest();
        a.setC(b);

        B bb = (B) a.getC();
        System.out.println("print  A-B-List before  serialize");
        for (int i = 0; i < bb.getList().size(); i++) {
            System.out.println("list[" + i + "]=" + bb.getList().get(i));
        }
        byte[] test = ProtoStuffSerializerUtil.serializer(a);
        ProtoStuffTest newA = ProtoStuffSerializerUtil.deserializer(test, ProtoStuffTest.class);
        bb = (B) newA.getC();
        System.out.println("print   A-B-List after serialize");
        for (int i = 0; i < bb.getList().size(); i++) {
            System.out.println("list[" + i + "]=" + bb.getList().get(i));
        }
        System.out.println();
    }
}


class B {
    private int num;
    private String str;
    private List<String> list;

    public B() {
        num = 3;
        str = "rpc";

        list = new ArrayList<String>();
        list.add("rpc-list");
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }


}
