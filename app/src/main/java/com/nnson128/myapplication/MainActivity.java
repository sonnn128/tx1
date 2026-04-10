package com.nnson128.myapplication;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextProductId, editTextProductName, editTextProductPrice;
    private Spinner spinnerCategory;
    private Button buttonAdd, buttonDelete, buttonStatistics;
    private ListView listViewProducts;
    private List<Product> productList;
    private ProductAdapter adapter;
    private int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            
            // Lấy dp sang pixel cho padding 16dp
            int paddingPx = (int) (16 * getResources().getDisplayMetrics().density);
            
            v.setPadding(systemBars.left + paddingPx, systemBars.top + paddingPx, systemBars.right + paddingPx, systemBars.bottom + paddingPx);
            return insets;
        });

        initializeViews();
        setupSpinner();
        setupListView();
        setupButtonListeners();
    }

    private void initializeViews() {
        editTextProductId = findViewById(R.id.editTextProductId);
        editTextProductName = findViewById(R.id.editTextProductName);
        editTextProductPrice = findViewById(R.id.editTextProductPrice);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonStatistics = findViewById(R.id.buttonStatistics);
        listViewProducts = findViewById(R.id.listViewProducts);
        
        productList = new ArrayList<>();
    }

    private void setupSpinner() {
        String[] categories = {"-- Chọn ngành học --", "CNTT", "Kế toán", "Điện tử"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, 
            android.R.layout.simple_spinner_item, categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(spinnerAdapter);
    }

    private void setupListView() {
        adapter = new ProductAdapter(this, productList);
        listViewProducts.setAdapter(adapter);
        
        // Handle ListView item click
        listViewProducts.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            Product product = productList.get(position);
            editTextProductId.setText(product.getId());
            editTextProductName.setText(product.getName());
            editTextProductPrice.setText(String.valueOf(product.getGpa()));
            
            // Set spinner to the product's category
            String[] categories = {"-- Chọn ngành học --", "CNTT", "Kế toán", "Điện tử"};
            for (int i = 0; i < categories.length; i++) {
                if (categories[i].equals(product.getMajor())) {
                    spinnerCategory.setSelection(i);
                    break;
                }
            }
        });
    }

    private void setupButtonListeners() {
        // Add button
        buttonAdd.setOnClickListener(v -> addProduct());
        
        // Delete button
        buttonDelete.setOnClickListener(v -> deleteProduct());
        
        // Statistics button
        buttonStatistics.setOnClickListener(v -> showStatistics());
    }

    private void addProduct() {
        String id = editTextProductId.getText().toString().trim();
        String name = editTextProductName.getText().toString().trim();
        String priceStr = editTextProductPrice.getText().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString();

        if (id.isEmpty() || name.isEmpty() || priceStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (spinnerCategory.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Vui lòng chọn ngành học", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double gpa = Double.parseDouble(priceStr);
            Product product = new Product(id, name, gpa, category);
            productList.add(product);
            adapter.notifyDataSetChanged();
            clearFields();
            Toast.makeText(this, "Thêm sinh viên thành công", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Điểm phải là số", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteProduct() {
        if (selectedPosition == -1) {
            Toast.makeText(this, "Vui lòng chọn sinh viên để xóa", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa sinh viên này không?")
                .setPositiveButton("Có", (dialog, which) -> {
                    productList.remove(selectedPosition);
                    adapter.notifyDataSetChanged();
                    clearFields();
                    selectedPosition = -1;
                    Toast.makeText(this, "Xóa sinh viên thành công", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Không", null)
                .show();
    }

    private void showStatistics() {
        if (productList.isEmpty()) {
            Toast.makeText(this, "Danh sách rỗng", Toast.LENGTH_SHORT).show();
            return;
        }

        int totalStudents = productList.size();
        double maxScore = productList.get(0).getGpa();
        
        for (Product product : productList) {
            if (product.getGpa() > maxScore) {
                maxScore = product.getGpa();
            }
        }

        String message = "Tổng số sinh viên: " + totalStudents + "\n" +
                        "Điểm trung bình lớn nhất: " + maxScore;

        new AlertDialog.Builder(this)
                .setTitle("Thông báo")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void clearFields() {
        editTextProductId.setText("");
        editTextProductName.setText("");
        editTextProductPrice.setText("");
        spinnerCategory.setSelection(0);
    }
}