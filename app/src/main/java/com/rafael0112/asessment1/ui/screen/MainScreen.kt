package com.rafael0112.asessment1.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rafael0112.asessment1.R
import com.rafael0112.asessment1.model.Shio
import com.rafael0112.asessment1.navigation.Screen
import com.rafael0112.asessment1.ui.theme.Asessment1Theme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.About.route)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.tentang_aplikasi),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
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

    Column(
        modifier = Modifier.fillMaxSize().padding(top = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = modifier.fillMaxWidth(0.5f).padding(top =8.dp)
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
                Text(text = stringResource(R.string.tanggal, dateFormat(timestamp)))
                ShioCalculate(timestamp)
            }
        }

    }
}

@Composable
fun dateFormat(timestamp: Long): String {
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
        Shio(R.string.monyet, R.drawable.monyet, R.string.deskripsi_monyet),
        Shio(R.string.ayam, R.drawable.ayam, R.string.deskripsi_ayam),
        Shio(R.string.anjing, R.drawable.anjing, R.string.deskripsi_anjing),
        Shio(R.string.babi, R.drawable.babi, R.string.deskripsi_babi),
        Shio(R.string.tikus, R.drawable.tikus, R.string.deskripsi_tikus),
        Shio(R.string.kerbau, R.drawable.kerbau, R.string.deskripsi_kerbau),
        Shio(R.string.macan, R.drawable.macan, R.string.deskripsi_macan),
        Shio(R.string.kelinci, R.drawable.kelinci, R.string.deskripsi_kelinci),
        Shio(R.string.naga, R.drawable.naga, R.string.deskripsi_naga),
        Shio(R.string.ular, R.drawable.ular, R.string.deskripsi_ular),
        Shio(R.string.kuda, R.drawable.kuda, R.string.deskripsi_kuda),
        Shio(R.string.kambing, R.drawable.kambing, R.string.deskripsi_kambing)
    )

    val index = year % 12
    val shio = shioList[index]

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Image(
            painter = painterResource(id = shio.imageResId),
            contentDescription = stringResource(id = shio.nameResId),
            modifier = Modifier.size(240.dp)
        )
        Text(
            text = stringResource(R.string.shio_name, stringResource(id = shio.nameResId)),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(id = shio.descriptionResId),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
    }

}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun GreetingPreview() {
    Asessment1Theme {
        MainScreen(rememberNavController())
    }
}