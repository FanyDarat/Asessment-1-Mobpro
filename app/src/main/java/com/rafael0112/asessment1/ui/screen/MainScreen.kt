package com.rafael0112.asessment1.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rafael0112.asessment1.R
import com.rafael0112.asessment1.model.Shio
import com.rafael0112.asessment1.ui.theme.Asessment1Theme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        ScreenContent(Modifier.padding(innerPadding))
    }
}

@Composable
fun ScreenContent(modifier: Modifier = Modifier) {
    var showDatePicker by remember {
        mutableStateOf(false)
    }

    var timestamp by remember {
        mutableLongStateOf(0)
    }

    val calendar = remember { Calendar.getInstance() }
    calendar.timeInMillis = timestamp

    val selectedCalendar = remember(timestamp) {
        Calendar.getInstance().apply {
            timeInMillis = timestamp
        }
    }

    val currentDate = Calendar.getInstance()

    Column {
        Surface(
            modifier = modifier.fillMaxWidth(0.5f).padding(top = 24.dp)
        ) {
            Button(
                onClick = {
                    showDatePicker = true
                }
            ) {
                Text(text = stringResource(R.string.button_umur))
                if (showDatePicker) {
                    DatePickerModalInput(
                        onDateSelected = {
                            if (it != null) {
                                timestamp = it
                            }
                        }
                    ) {
                        showDatePicker = false
                    }
                }
            }

        }
        if (timestamp != 0L) {
            if (selectedCalendar.after(currentDate)) {
                Text(text = stringResource(R.string.error_code))
            } else {
                Text(text = stringResource(R.string.tanggal, formateDate(timestamp)))
                ShioCalculate(timestamp)
            }
        }

    }
}

@Composable
fun formateDate(timestamp: Long): String {
    if (timestamp == 0L) return ""
    val date = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return date.format(Date(timestamp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModalInput(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}



@Composable
fun ShioCalculate(timestamp: Long) {
    if (timestamp == 0L) {
        return
    }

    val calendar = remember(timestamp) {
        Calendar.getInstance().apply {
            timeInMillis = timestamp
        }
    }

    val year = calendar.get(Calendar.YEAR)

    val shioList = listOf(
        Shio(R.string.tikus, R.drawable.tikus),
        Shio(R.string.kerbau, R.drawable.kerbau),
        Shio(R.string.macan, R.drawable.macan),
        Shio(R.string.kelinci, R.drawable.kelinci),
        Shio(R.string.naga, R.drawable.naga),
        Shio(R.string.ular, R.drawable.ular),
        Shio(R.string.kuda, R.drawable.kuda),
        Shio(R.string.kambing, R.drawable.kambing),
        Shio(R.string.monyet, R.drawable.monyet),
        Shio(R.string.ayam, R.drawable.ayam),
        Shio(R.string.anjing, R.drawable.anjing),
        Shio(R.string.babi, R.drawable.babi)
    )
    
    val index = year % 12
    val shio = shioList[index]

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = shio.imageResId),
            contentDescription = stringResource(id = shio.nameResId),
            modifier = Modifier.size(250.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.shio_name, stringResource(id = shio.nameResId), year),
            style = MaterialTheme.typography.bodyLarge
        )
    }

}




@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun GreetingPreview() {
    Asessment1Theme {
        MainScreen()
    }
}