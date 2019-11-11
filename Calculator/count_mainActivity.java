package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Stack;

public class count_mainActivity extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.count_layout);

        //监听器-实现接口方式

        Button bt_0=(Button)findViewById(R.id.bt_0);
        bt_0.setOnClickListener(this);
        Button bt_1=(Button)findViewById(R.id.bt_1);
        bt_1.setOnClickListener(this);
        Button bt_2=(Button)findViewById(R.id.bt_2);
        bt_2.setOnClickListener(this);
        Button bt_3=(Button)findViewById(R.id.bt_3);
        bt_3.setOnClickListener(this);
        Button bt_4=(Button)findViewById(R.id.bt_4);
        bt_4.setOnClickListener(this);
        Button bt_5=(Button)findViewById(R.id.bt_5);
        bt_5.setOnClickListener(this);
        Button bt_6=(Button)findViewById(R.id.bt_6);
        bt_6.setOnClickListener(this);
        Button bt_7=(Button)findViewById(R.id.bt_7);
        bt_7.setOnClickListener(this);
        Button bt_8=(Button)findViewById(R.id.bt_8);
        bt_8.setOnClickListener(this);
        Button bt_9=(Button)findViewById(R.id.bt_9);
        bt_9.setOnClickListener(this);

        Button bt_pow=(Button)findViewById(R.id.bt_pow);
        bt_pow.setOnClickListener(this);
        Button bt_lg=(Button)findViewById(R.id.bt_lg);
        bt_lg.setOnClickListener(this);
        Button bt_ln=(Button)findViewById(R.id.bt_ln);
        bt_ln.setOnClickListener(this);
        Button bt_lb=(Button)findViewById(R.id.bt_lb);
        bt_lb.setOnClickListener(this);
        Button bt_rb=(Button)findViewById(R.id.bt_rb);
        bt_rb.setOnClickListener(this);

        Button bt_sin=(Button)findViewById(R.id.bt_sin);
        bt_sin.setOnClickListener(this);
        Button bt_AC=(Button)findViewById(R.id.bt_AC);
        bt_AC.setOnClickListener(this);
        Button bt_CE=(Button)findViewById(R.id.bt_CE);
        bt_CE.setOnClickListener(this);
        Button bt_mod=(Button)findViewById(R.id.bt_mod);
        bt_mod.setOnClickListener(this);
        Button bt_div=(Button)findViewById(R.id.bt_div);
        bt_div.setOnClickListener(this);

        Button bt_cos=(Button)findViewById(R.id.bt_cos);
        bt_cos.setOnClickListener(this);
        Button bt_tan=(Button)findViewById(R.id.bt_tan);
        bt_tan.setOnClickListener(this);
        Button bt_PI=(Button)findViewById(R.id.bt_PI);
        bt_PI.setOnClickListener(this);

        Button bt_mul=(Button)findViewById(R.id.bt_mul);
        bt_mul.setOnClickListener(this);
        Button bt_sub=(Button)findViewById(R.id.bt_sub);
        bt_sub.setOnClickListener(this);
        Button bt_add=(Button)findViewById(R.id.bt_add);
        bt_add.setOnClickListener(this);

        Button bt_point=(Button)findViewById(R.id.bt_point);
        bt_point.setOnClickListener(this);
        Button bt_eq=(Button)findViewById(R.id.bt_eq);
        bt_eq.setOnClickListener(this);

        //计算、换算的转换(显示Intent)
        Button countToConversion=(Button) findViewById(R.id.bt_more);
        countToConversion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(count_mainActivity.this,conversionActivity.class);
                startActivity(intent);
            }
        });
    }


    //监听器-实现接口方式(Button)
    int old_len=0;
    @Override
    public void onClick(View v) {
        EditText op=(EditText)findViewById(R.id.op_View);
        switch (v.getId()){
            case R.id.bt_0: op.append("0");break;
            case R.id.bt_1: op.append("1");break;
            case R.id.bt_2: op.append("2");break;
            case R.id.bt_3: op.append("3");break;
            case R.id.bt_4: op.append("4");break;
            case R.id.bt_5: op.append("5");break;
            case R.id.bt_6: op.append("6");break;
            case R.id.bt_7: op.append("7");break;
            case R.id.bt_8: op.append("8");break;
            case R.id.bt_9: op.append("9");break;
            case R.id.bt_pow: op.append("^");break;
            case R.id.bt_lg: op.append("lg");break;
            case R.id.bt_ln: op.append("ln");break;
            case R.id.bt_lb: op.append("(");break;
            case R.id.bt_rb: op.append(")");break;
            case R.id.bt_sin: op.append("sin");break;
            case R.id.bt_AC://删除本次运算的数据
                op.setText("");

                break;
            case R.id.bt_CE://不为空时删除一个符号
                String str1=op.getText().toString();
                int len=str1.length();
                if(len!=0){
                    op.setText(str1.substring(0,len-1));
                }
                break;
            case R.id.bt_mod: op.append("%");break;
            case R.id.bt_div: op.append("/");break;
            case R.id.bt_cos: op.append("cos");break;
            case R.id.bt_tan: op.append("tan");break;
            case R.id.bt_PI: op.append("3.14159265");break;
            case R.id.bt_mul: op.append("*");break;
            case R.id.bt_sub: op.append("-");break;
            case R.id.bt_add: op.append("+");break;
            case R.id.bt_point: op.append(".");break;
            case R.id.bt_eq:
                op.append("=");//进行运算
                String str=op.getText().toString().substring(old_len);
                {//算术表达式求值
                    Stack<String> stack1=new Stack<>();//运算符栈
                    Stack<Double> stack2=new Stack<>();//运算数栈
                    int i=0;
                    stack1.push("#");
                    while(str.charAt(i)!='='||!stack1.peek().equals("#")){
                        //操作数压栈
                        if(str.charAt(i)>='0'&&str.charAt(i)<='9'){
                            double value=0.0;
                            int flag=1;//flag=1整数部分;flag<0小数点后flag位
                            while((str.charAt(i)>='0'&&str.charAt(i)<='9')||str.charAt(i)=='.'){
                                if(str.charAt(i)=='.'){
                                    flag=-1;
                                }
                                else if(flag==1)
                                    value=value*10+str.charAt(i)-'0';
                                else{
                                    value+=Math.pow(10,flag)* (str.charAt(i)-'0');
                                    flag--;
                                }
                                i++;
                            }
                            stack2.push(value);
                            //op.append("\n"+stack2.peek());
                        }
                        //求结果
                        if(str.charAt(i)=='='){
                            while(!stack1.peek().equals("#")){
                                if(stack1.peek().length()==1){
                                    double op2 = stack2.peek();
                                    stack2.pop();//操作数出栈
                                    double op1 = stack2.peek();
                                    stack2.pop();//操作数出栈
                                    switch (stack1.peek()) {
                                        case "+":
                                            stack2.push(op1 + op2);
                                            break;
                                        case "-":
                                            stack2.push(op1 - op2);
                                            break;
                                        case "*":
                                            stack2.push(op1 * op2);
                                            break;
                                        case "/":
                                            stack2.push(op1 / op2);
                                            break;
                                        case "^":
                                            stack2.push(Math.pow(op1, op2));
                                            break;
                                        case "%":
                                            int _op1 = (int) op1;
                                            int _op2 = (int) op2;
                                            stack2.push((double) _op1 - (_op1 / _op2) * _op2);
                                            break;
                                        default:
                                            break;
                                    }
                                    stack1.pop();//运算符出栈
                                }
                                else{
                                    double op1 = stack2.peek();
                                    stack2.pop();//操作数出栈
                                    switch (stack1.peek()) {
                                        case "sin":
                                            stack2.push(Math.sin(op1));
                                            break;
                                        case "cos":
                                            stack2.push(Math.cos(op1));
                                            break;
                                        case "tan":
                                            stack2.push(Math.tan(op1));
                                            break;
                                        case "lg":
                                            stack2.push(Math.log10(op1));
                                            break;
                                        case "ln":
                                            stack2.push(Math.log(op1));
                                            break;
                                    }
                                    stack1.pop();//操作符出栈
                                }
                            }
                            old_len=op.getText().length();
                            op.append("\n"+String.format("%.2f", stack2.peek()));
                            break;
                        }
                        //操作符压栈
                        {
                            //sin.cos,tan,lg,ln,（，直接压栈
                            if (str.charAt(i) == 's' || str.charAt(i) == 'c' || str.charAt(i) == 't') {//sin,cos,tan直接压栈
                                stack1.push(str.substring(i, i + 3));
                                i += 3;
                            }
                            if (str.charAt(i) == 'l') {//lg,ln直接入栈
                                stack1.push(str.substring(i, i + 2));
                                i += 2;
                            }
                            if (str.charAt(i) == '(') {//左括号直接压栈
                                stack1.push("(");
                                i += 1;
                            }
                            //），出栈直到左括号出栈
                            if (str.charAt(i) == ')') {
                                while(!stack1.peek().equals("(")){
                                    if(stack1.peek().length()==1){
                                        double op2 = stack2.peek();
                                        stack2.pop();//操作数出栈
                                        double op1 = stack2.peek();
                                        stack2.pop();//操作数出栈
                                        switch (stack1.peek()) {
                                            case "+":
                                                stack2.push(op1 + op2);
                                                break;
                                            case "-":
                                                stack2.push(op1 - op2);
                                                break;
                                            case "*":
                                                stack2.push(op1 * op2);
                                                break;
                                            case "/":
                                                stack2.push(op1 / op2);
                                                break;
                                            case "^":
                                                stack2.push(Math.pow(op1, op2));
                                                break;
                                            case "%":
                                                int _op1 = (int) op1;
                                                int _op2 = (int) op2;
                                                stack2.push((double) _op1 - (_op1 / _op2) * _op2);
                                                break;
                                            default:
                                                break;
                                        }
                                        stack1.pop();//运算符出栈
                                    }
                                    else{
                                        double op1 = stack2.peek();
                                        stack2.pop();//操作数出栈
                                        switch (stack1.peek()) {
                                            case "sin":
                                                stack2.push(Math.sin(op1));
                                                break;
                                            case "cos":
                                                stack2.push(Math.cos(op1));
                                                break;
                                            case "tan":
                                                stack2.push(Math.tan(op1));
                                                break;
                                            case "lg":
                                                stack2.push(Math.log10(op1));
                                                break;
                                            case "ln":
                                                stack2.push(Math.log(op1));
                                                break;
                                        }
                                        stack1.pop();//操作符出栈
                                    }
                                }
                                stack1.pop();
                                i++;
                            }
                            //+,-,*,/,^,%，先于stack1栈顶比较优先级，大再入栈
                            if (str.charAt(i) == '+' || str.charAt(i) == '-' || str.charAt(i) == '*' || str.charAt(i) == '/' || str.charAt(i) == '^' || str.charAt(i) == '%') {//二元运算
                                while (1 == 1) {
                                    //运算符压栈（优先级大于栈顶）
                                    if (str.charAt(i) == '+' || str.charAt(i) == '-') {
                                        if (stack1.peek().equals("#")||stack1.peek().equals("(")) {
                                            stack1.push(String.valueOf(str.charAt(i)));
                                            i++;
                                            break;
                                        }
                                    }
                                    if (str.charAt(i) == '*' || str.charAt(i) == '/'){
                                        if(stack1.peek().equals("+") ||stack1.peek().equals("-")|| stack1.peek().equals("#") ||stack1.peek().equals("(")){
                                            stack1.push(String.valueOf(str.charAt(i)));
                                            i++;
                                            break;
                                        }
                                    }
                                    if (str.charAt(i) == '^' || str.charAt(i) == '%') {
                                        if (stack1.peek().equals("+")|| stack1.peek().equals("-")|| stack1.peek().equals("*") || stack1.peek().equals("/")|| stack1.peek().equals("#")||stack1.peek().equals("(")) {
                                            stack1.push(String.valueOf(str.charAt(i)));
                                            i++;
                                            break;
                                        }
                                    }
                                    //sin,cos,tan,ln,lg出栈运算(一元运算)
                                    if (stack1.peek().length() != 1&&str.charAt(i)!='=') {
                                        double op1 = stack2.peek();
                                        stack2.pop();//操作数出栈
                                        switch (stack1.peek()) {
                                            case "sin":
                                                stack2.push(Math.sin(op1));
                                                break;
                                            case "cos":
                                                stack2.push(Math.cos(op1));
                                                break;
                                            case "tan":
                                                stack2.push(Math.tan(op1));
                                                break;
                                            case "lg":
                                                stack2.push(Math.log10(op1));
                                                break;
                                            case "ln":
                                                stack2.push(Math.log(op1));
                                                break;
                                        }
                                        stack1.pop();//操作符出栈
                                        continue;//结束本次循环
                                    }
                                    //二元运算符出栈（优先级小于栈顶元素运算符）
                                    if (stack1.peek().length() == 1&&str.charAt(i)!='=') {
                                        double op2 = stack2.peek();
                                        stack2.pop();//操作数出栈
                                        double op1 = stack2.peek();
                                        stack2.pop();//操作数出栈
                                            switch (stack1.peek()) {
                                                case "+":
                                                    stack2.push(op1 + op2);
                                                    break;
                                                case "-":
                                                    stack2.push(op1 - op2);
                                                    break;
                                                case "*":
                                                    stack2.push(op1 * op2);
                                                    break;
                                                case "/":
                                                    stack2.push(op1 / op2);
                                                    break;
                                                case "^":
                                                    stack2.push(Math.pow(op1, op2));
                                                    break;
                                                case "%":
                                                    int _op1 = (int) op1;
                                                    int _op2 = (int) op2;
                                                    stack2.push((double) _op1 - (_op1 / _op2) * _op2);
                                                    break;
                                                default:
                                                    break;
                                            }
                                        stack1.pop();//运算符出栈
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    //菜单(help,exit)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_help:
                Toast.makeText(this,"This is help",Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_exit:
                break;
                default:
                    break;
        }
        return true;
    }
}
