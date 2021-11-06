package com.company;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Calculations {

    private Stack st = new Stack();
    private Queue qe = new LinkedList();
    private Queue Number = new LinkedList();
    private Stack ErrorСhecking = new Stack();
    char [] StringToCharArr;

    public Calculations(String str)
    {
        StringToCharArr = str.toCharArray();
    }
    //Проверка ошибок:
    //1.Деление на ноль
    //2.Подряд идущие знаки
    //3.Соответсвие скобок
    public boolean ErrorCheck()
    {
        Stack staples = new Stack();
        for(char elem: StringToCharArr) {
            if(elem>='0'&&elem<='9') {
                if(elem=='0'){
                    if(ErrorСhecking.isEmpty())
                        ErrorСhecking.add(elem);
                    else if((char) ErrorСhecking.peek()!='#') {
                        if((char) ErrorСhecking.peek()=='/')
                            return false;
                        ErrorСhecking.add(elem);
                    }
                    else ErrorСhecking.add('#');
                }else
                ErrorСhecking.add('#');
            }else
            if(elem=='-'||elem=='+'||elem=='*'||elem=='/') {
                if (ErrorСhecking.isEmpty()) {
                    return false;
                }
                char h=(char) ErrorСhecking.peek();
                if(h=='-'||h=='+'||h=='*'||h=='/')
                    return false;
                ErrorСhecking.add(elem);
            }else
            if(elem=='('||elem==')') {
                if(elem==')') {
                    if (staples.isEmpty()) {
                        return false;
                    } else staples.pop();
                }
                else if(elem=='(')
                    staples.push(elem);
            }
            else
            {
                return false;
            }
        }
        if (ErrorСhecking.isEmpty()) {
            return false;
        }
        char h=(char) ErrorСhecking.peek();
        if(h=='-'||h=='+'||h=='*'||h=='/') {
            return false;
        }
        if(!staples.isEmpty())
        {
            return false;
        }
        return true;
    }
    // Перевод из инфиксной в постфиксную
    public Queue ToPost()
    {
        for(char elem: StringToCharArr)
        {
            if((elem>='0'&&elem<='9'))
                Number.add(elem);
            else {
                if (!Number.isEmpty()) {
                    qe.add(CharToNumber());
                }
                if (elem == '(') {
                    st.push(elem);
                }
                if (elem == ')') {
                    pop1();
                }
                if(elem=='+'||elem=='-') {
                    if (st.empty() || (char) st.peek() == '(') {
                        st.push(elem);
                    }
                    else
                    {
                        pop2();
                        st.push(elem);
                    }
                }
                if(elem=='*'||elem=='/') {
                    if (st.empty() || (char) st.peek() == '(')
                        st.push(elem);
                    else {
                        pop3();
                        st.push(elem);
                    }
                }
            }
        }
        if (!Number.isEmpty()) {
            qe.add(CharToNumber());
        }
        while (!st.empty())
        {
            qe.add((char)st.pop());
        }
        return qe;
    }
    //Перевод (Char) в (Double)
    private double CharToNumber()
    {
        double sum = 0;
        while(!Number.isEmpty())
        {
            int res=1;
            for(int i = 0; i < Number.size()-1; i++)
            {
                res*=10;
            }
            int temp = (char) Number.poll() - 48;
            res*=temp;
            sum+=res;
        }
        return sum;
    }
    //Вычисление постфиксного выражения
    public double ToRes()
    {
        Stack result = new Stack();

        while(!qe.isEmpty())
        {
            double a, b;
            String ch = String.valueOf(qe.peek());
            switch (ch) {
                case "+":
                    a = (double) result.pop();
                    b = (double) result.pop();
                    result.push(a+b);
                    break;
                case "-":
                    a = (double) result.pop();
                    b = (double) result.pop();
                    result.push(b-a);
                    break;
                case "*":
                    a = (double) result.pop();
                    b = (double) result.pop();
                    result.push(a*b);
                    break;
                case "/":
                    a = (double) result.pop();
                    b = (double) result.pop();
                    result.push(b/a);
                    break;
                default:
                    double res = (double)qe.peek();
                    result.push(res);
                    break;
            }
            qe.poll();
        }
        return (double)result.peek();
    }
    private void pop1()
    {
        while (!st.empty())
        {
            char temp = (char)st.peek();
            if(temp == '(') {
                st.pop();
                break;
            }
            else
            {
                qe.add(temp);
                st.pop();
            }
        }

    }
    private void pop2()
    {
        while (!st.empty())
        {
            char temp = (char)st.peek();
            if(temp == '(') {
                break;
            }
            else
            {
                qe.add(temp);
                st.pop();
            }
        }

    }
    private void pop3()
    {
        while (!st.empty())
        {
            char temp = (char)st.peek();
            if(temp == '('||temp == '+'||temp == '-') {
                break;
            }
            else
            {
                qe.add(temp);
                st.pop();
            }
        }
    }

}
