package com.example.bai7;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private int id;
    private int status;
    Button btnthem, btnsua, btnxoa;
    EditText edtTitle, edtContent, edtDate,edtType;
    ListView lvTodo;



    private void refreshListView() {
        ToDoDAO todoDAO = new ToDoDAO(MainActivity.this);
        ArrayList<ToDo> list = todoDAO.getListToDo();
        ToDoAdapter toDoAdapter = new ToDoAdapter(MainActivity.this, list);
        lvTodo.setAdapter(toDoAdapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvTodo = findViewById(R.id.lvTodo);
        ToDoDAO todoDAO = new ToDoDAO(this);
        ArrayList<ToDo> list = todoDAO.getListToDo();
        ToDoAdapter toDoAdapter  = new ToDoAdapter(this, list);
        lvTodo.setAdapter(toDoAdapter);

        Button btnthem = findViewById(R.id.btnthem);
        Button btnsua = findViewById(R.id.btnsua);
        Button btnxoa = findViewById(R.id.btnxoa);
        final EditText edtTitle = findViewById(R.id.edtTitle);
        final EditText edtContent = findViewById(R.id.edtContent);
        final EditText edtDate = findViewById(R.id.edtDate);
        final EditText edtType = findViewById(R.id.edtType);

        lvTodo.setOnItemClickListener( new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ToDo selectedToDo = (ToDo) parent.getItemAtPosition(position);


                edtTitle.setText(selectedToDo.getTitle());
                edtContent.setText(selectedToDo.getContent());
                edtDate.setText(selectedToDo.getDate());
                edtType.setText(selectedToDo.getType());

                MainActivity.this.id = selectedToDo.getId();
                MainActivity.this.status = selectedToDo.getStatus();
            }
        });

        btnthem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Lấy dữ liệu từ EditText
                String title = edtTitle.getText().toString();
                String content = edtContent.getText().toString();
                String date = edtDate.getText().toString();
                String type = edtType.getText().toString();
                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content) || TextUtils.isEmpty(date) || TextUtils.isEmpty(type)) {
                    // Hiển thị thông báo lỗi nếu có trường nào đó rỗng
                    Toast.makeText(MainActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    // Tạo một đối tượng ToDo mới
                    ToDo newToDo = new ToDo(0, title, content, date, type, 0);

                    // Thêm ToDo vào cơ sở dữ liệu
                    ToDoDAO todoDAO = new ToDoDAO(MainActivity.this);
                    boolean isSuccess = todoDAO.addToDo(newToDo);

                    if (isSuccess) {
                        // Nếu thêm thành công, hiển thị thông báo thành công
                        Toast.makeText(MainActivity.this, "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                        // Cập nhật ListView
                        ArrayList<ToDo> list = todoDAO.getListToDo();
                        ToDoAdapter toDoAdapter = new ToDoAdapter(MainActivity.this, list);
                        lvTodo.setAdapter(toDoAdapter);
                    } else {
                        // Nếu thêm không thành công, hiển thị thông báo lỗi
                        Toast.makeText(MainActivity.this, "Thêm mới thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy dữ liệu từ EditText
                String title = edtTitle.getText().toString();
                String content = edtContent.getText().toString();
                String date = edtDate.getText().toString();
                String type = edtType.getText().toString();

                // Kiểm tra xem có trường nào đó rỗng không
                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content) || TextUtils.isEmpty(date) || TextUtils.isEmpty(type)) {
                    // Hiển thị thông báo lỗi nếu có trường nào đó rỗng
                    Toast.makeText(MainActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    // Tạo và hiển thị AlertDialog để xác nhận việc cập nhật
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Bạn có  muốn cập nhật không?")
                            .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Xác nhận cập nhật, gọi phương thức Update trong ToDoDAO
                                    ToDo updatedToDo = new ToDo(id, title, content, date, type, status);
                                    ToDoDAO todoDAO = new ToDoDAO(MainActivity.this);
                                    todoDAO.Update(updatedToDo);
                                    refreshListView();

                                    // Xóa dữ liệu trong EditTexts
                                    edtTitle.setText("");
                                    edtContent.setText("");
                                    edtDate.setText("");
                                    edtType.setText("");
                                }
                            })
                            .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Không làm gì khi nhấn "Không"
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
            }
        });


        btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo và hiển thị AlertDialog để xác nhận việc xóa
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Bạn có muốn xóa không?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Xác nhận xóa, gọi phương thức Delete trong ToDoDAO
                                ToDoDAO todoDAO = new ToDoDAO(MainActivity.this);
                                todoDAO.Delete(id);
                                refreshListView();
                                // Xóa dữ liệu trong EditTexts
                                edtTitle.setText("");
                                edtContent.setText("");
                                edtDate.setText("");
                                edtType.setText("");
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Không làm gì khi nhấn "Không"
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });




    }
}








