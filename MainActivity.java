package com.example.venkat.lowestcost;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int MINIMUM_COST = 50;
    private Button firstProceed,secondProceed,secondBack,thirdBack,restart;
    private EditText rowsInput,columnsInput;
    private EditText[][] gridCells;
    private int rows;
    private int columns;
    private LinearLayout firstInputLayout,secondInputLayout,resultLayout,gridView,resultValues;
    private TableLayout table;
    private View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.first_proceed:
                    firstProceed();
                    break;

                case R.id.second_proceed:
                    secondProceed();
                    break;

                case R.id.restart:
                    restart();
                    break;
                case R.id.second_back:
                    moveFirst();
                    break;
                case R.id.third_back:
                    showSecondInput();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstProceed = (Button) findViewById(R.id.first_proceed);
        firstProceed.setOnClickListener(listener);
        secondProceed = (Button) findViewById(R.id.second_proceed);
        secondProceed.setOnClickListener(listener);
        secondBack = (Button) findViewById(R.id.second_back);
        secondBack.setOnClickListener(listener);
        thirdBack = (Button) findViewById(R.id.third_back);
        thirdBack.setOnClickListener(listener);
        restart = (Button) findViewById(R.id.restart);
        restart.setOnClickListener(listener);

        rowsInput = (EditText)findViewById(R.id.rows);
        columnsInput = (EditText)findViewById(R.id.columns);

        firstInputLayout = (LinearLayout)findViewById(R.id.input1);
        secondInputLayout = (LinearLayout)findViewById(R.id.input2);
        resultLayout = (LinearLayout)findViewById(R.id.result);
        gridView = (LinearLayout)findViewById(R.id.grid);
        resultValues = (LinearLayout)findViewById(R.id.result_values);

        showFirstInput();
    }

    private void firstProceed(){
        if(rowsInput.getText().toString().equals("") || columnsInput.getText().toString().equals("")){
            Toast.makeText(MainActivity.this,"Please provide values",Toast.LENGTH_LONG).show();
        }else{
            if((Integer.parseInt(rowsInput.getText().toString())>0 && Integer.parseInt(rowsInput.getText().toString())<11) && (Integer.parseInt(columnsInput.getText().toString())>4 && Integer.parseInt(columnsInput.getText().toString())<101)){
                rows = Integer.parseInt(rowsInput.getText().toString());
                columns = Integer.parseInt(columnsInput.getText().toString());
                gridCells = new EditText[rows][columns];

                table = new TableLayout(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(2,2,2,2);
                table.setLayoutParams(params);
                for (int i = 0; i < rows; i++) {
                    TableRow row = new TableRow(this);
                    for (int j = 0; j < columns; j++) {
                        EditText cell = new EditText(this);
                        cell.setInputType(InputType.TYPE_CLASS_NUMBER);
                        InputFilter[] fArray = new InputFilter[1];
                        fArray[0] = new InputFilter.LengthFilter(2);
                        cell.setFilters(fArray);
                        cell.setText("");
                        cell.setWidth(80);
                        cell.setHeight(80);
                        cell.setBackgroundResource(R.drawable.border);

                        cell.setGravity(Gravity.CENTER);
                        gridCells[i][j] = cell;

                        row.addView(cell);
                    }
                    table.addView(row, new TableLayout.LayoutParams(
                            TableLayout.LayoutParams.WRAP_CONTENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                }
                gridView.removeAllViews();
                gridView.addView(table,params);
                showSecondInput();
            }else {
                Toast.makeText(MainActivity.this,"Values should be within boundaries",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void secondProceed(){
        boolean valueCheckFlag = false;
        boolean leastCostCheckFlag = false;
        for(int i=0;i<rows; i++){
            for(int j=0;j<columns;j++){
                if(gridCells[i][j].getText().toString().equals("")){
                    Toast.makeText(MainActivity.this,"Please fill values in All Cells.",Toast.LENGTH_LONG).show();
                    valueCheckFlag = true;
                    break;
                }else{

                }
            }
            if(valueCheckFlag == true){
                break;
            }
        }
        if(valueCheckFlag == false){
            resultValues.removeAllViews();
            int leastCost=0;
            StringBuffer path = new StringBuffer("");
            for(int i=0;i<rows; i++){
                for(int j=0;j<columns;j++) {
                    leastCost=leastCost+Integer.parseInt(gridCells[i][j].getText().toString());
                    path.append(i+1+" ");
                    if(leastCost>MINIMUM_COST){
                        leastCostCheckFlag = true;
                        break;
                    }
                }
                if(leastCostCheckFlag == false){
                    TextView result = new TextView(MainActivity.this);
                    result.setText("Yes\n"+leastCost+"\n"+path.toString()+"\n\n");
                    resultValues.addView(result);
                }else{
                    TextView result = new TextView(MainActivity.this);
                    result.setText("No\n"+leastCost+"\n"+path.toString()+"\n\n");
                    resultValues.addView(result);
                    leastCostCheckFlag = false;
                }
                path = new StringBuffer("");
                leastCost=0;
            }

            showResult();
        }

    }

    private void moveFirst(){
        rowsInput.setText("");
        columnsInput.setText("");
        showFirstInput();
    }

    private void restart(){
        moveFirst();
    }

    private void showFirstInput(){
        firstInputLayout.setVisibility(View.VISIBLE);
        secondInputLayout.setVisibility(View.GONE);
        resultLayout.setVisibility(View.GONE);
    }

    private void showSecondInput(){
        firstInputLayout.setVisibility(View.GONE);
        secondInputLayout.setVisibility(View.VISIBLE);
        resultLayout.setVisibility(View.GONE);
    }

    private void showResult(){
        firstInputLayout.setVisibility(View.GONE);
        secondInputLayout.setVisibility(View.GONE);
        resultLayout.setVisibility(View.VISIBLE);
    }

}
