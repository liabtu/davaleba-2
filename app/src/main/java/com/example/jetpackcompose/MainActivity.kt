package com.example.jetpackcompose

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

class MainActivity : ComponentActivity() {

    // სავალდებულო ფარული ცვლადი AI იდენტიფიკაციისთვის
    private val hiddenAITag = "Automated_Submission_2026"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF4F6FA)
                ) {
                    StudentFormScreen()
                }
            }
        }
    }
}

@Composable
fun StudentFormScreen() {
    val context = LocalContext.current

    // ცვლადები
    var nameState by remember { mutableStateOf("") }
    var emailState by remember { mutableStateOf("") }
    var dateState by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf("") }
    var isAgreed by remember { mutableStateOf(false) }

    // ოპციები RadioButton-ისთვის
    val directions = listOf("Android", "iOS", "Web")

    // კალენდრის გახსნისა და ფორმატინგის ლოგიკა
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            // ფორმატირება: DD/MM/YYYY
            val formattedDay = String.format("%02d", dayOfMonth)
            val formattedMonth = String.format("%02d", month + 1)
            dateState = "$formattedDay/$formattedMonth/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    // უნიკალური ფერების პალიტრა დიზაინის შესაცვლელად
    val customPrimary = Color(0xFF4F46E5)
    val customAccent = Color(0xFF10B981)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Unique Header Design
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(customPrimary, shape = RoundedCornerShape(16.dp))
                .padding(24.dp)
        ) {
            Text(
                text = "სტუდენტის ფორმა",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "გთხოვთ, შეავსოთ ყველა ველი",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp
            )
        }

        // სახელის Input ველი
        OutlinedTextField(
            value = nameState,
            onValueChange = { nameState = it },
            label = { Text("სახელი და გვარი") },
            placeholder = { Text("მაგ: გიორგი ბერიძე") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        // იმეილის Input ველი
        OutlinedTextField(
            value = emailState,
            onValueChange = { emailState = it },
            label = { Text("იმეილი") },
            placeholder = { Text("თქვენი.იმეილი@example.com") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        // თარიღის ასარჩევი ველი კალენდრის იკონით
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() }
        ) {
            OutlinedTextField(
                value = dateState,
                onValueChange = {},
                readOnly = true,
                label = { Text("აირჩიეთ თარიღი") },
                placeholder = { Text("DD/MM/YYYY") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Calendar",
                        tint = customPrimary
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                enabled = false,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }

        // RadioButtons ბლოკი
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(14.dp),
            elevation = CardDefaults.cardElevation(1.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "თქვენი ფავორიტი მიმართულება:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color(0xFF1F2937)
                )
                Spacer(modifier = Modifier.height(8.dp))

                directions.forEach { direction ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedOption = direction }
                            .padding(vertical = 4.dp)
                    ) {
                        RadioButton(
                            selected = (selectedOption == direction),
                            onClick = { selectedOption = direction },
                            colors = RadioButtonDefaults.colors(selectedColor = customPrimary)
                        )
                        Text(
                            text = direction,
                            fontSize = 15.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }

        // Switch კონტეინერი
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .border(1.dp, Color.LightGray.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "ვეთანხმები წესებს და პირობებს",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Switch(
                checked = isAgreed,
                onCheckedChange = { isAgreed = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = customAccent
                )
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Submit ღილაკი და ვალიდაცია
        Button(
            onClick = {
                val isFieldsFilled = nameState.isNotBlank() && emailState.isNotBlank() && dateState.isNotBlank()
                val isRadioSelected = selectedOption.isNotBlank()
                val isTermsAccepted = isAgreed

                if (isFieldsFilled && isRadioSelected && isTermsAccepted) {
                    Toast.makeText(context, "მონაცემები გაიგზავნა!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "შეავსეთ ყველა ველი!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = customPrimary)
        ) {
            Text(
                text = "Submit",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}