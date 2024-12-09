package com.example.kriya

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kriya.Frequency.*
import com.example.kriya.ui.theme.KriyaTheme
import java.time.LocalTime
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.TimeSource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KriyaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HabitsList(
                        modifier = Modifier.padding(innerPadding),
                        habits = Habit.previewHabitList // TODO change this to real data
                    )
                }
            }
        }
    }
}

@Composable
fun HabitsList(habits: List<Habit>, modifier: Modifier = Modifier) {
    LazyColumn (modifier = modifier) {
        itemsIndexed(habits, key = {_, it -> it.title}) { index, habit ->
            HabitListItem(habit, {})
        }
    }
}

@Composable
fun HabitListItem(habit: Habit, navigateToDetail: (Habit) -> Unit) {
    ListItem(
        modifier = Modifier.clickable {
            navigateToDetail(habit)
        },
        headlineContent = { Text(habit.title) },
        supportingContent = { Text(habit.duration.toString()) },
        leadingContent = {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray)
            )
        },
        trailingContent = {
            if (habit.frequency != UNDEFINED)
                Text(habit.frequency.name)
        }
    )
}

enum class Frequency(var times: Int = 1) {
    DAILY, WEEKLY, BI_WEEKLY, MONTHLY, YEARLY, UNDEFINED
}

data class Habit(var title: String, var duration: Duration = 15.minutes, var startTime: LocalTime? = null, var frequency: Frequency = UNDEFINED) {
    companion object {
        val previewHabitList = listOf(
            Habit("Water plants", frequency = DAILY, duration = 5.minutes),
            Habit("Read a Page a day", frequency = DAILY, duration = 20.minutes),
            Habit("Brush twice a day", duration = 5.minutes),
            Habit("Weekly Money review", frequency = WEEKLY, duration = 30.minutes),
        )

    }
}

@Preview(showBackground = true, device = Devices.PIXEL, showSystemUi = true)
@Composable
fun HomePreview() {
    KriyaTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            HabitsList(
                modifier = Modifier.padding(innerPadding),
                habits = Habit.previewHabitList // TODO change this to real data
            )
        }
    }
}