package com.example.cellularfilling.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cellularfilling.R
import com.example.cellularfilling.domain.entity.Cell
import com.example.cellularfilling.domain.entity.CellState
import com.example.cellularfilling.presentation.ui.theme.aliveImgFirst
import com.example.cellularfilling.presentation.ui.theme.aliveImgSecond
import com.example.cellularfilling.presentation.ui.theme.btnPurple
import com.example.cellularfilling.presentation.ui.theme.deadImgFirst
import com.example.cellularfilling.presentation.ui.theme.deadImgSecond
import com.example.cellularfilling.presentation.ui.theme.liveImgFirst
import com.example.cellularfilling.presentation.ui.theme.liveImgSecond

@Composable
fun CellScreen(modifier: Modifier = Modifier) {
    val viewModel: CellViewModel = viewModel<CellViewModel>()
    val state = viewModel.state.collectAsState(CellScreenState.Initial)
    val listState = rememberLazyListState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF310050), Color(0xFF000000))
                )
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.title),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        when (val currentState = state.value) {
            CellScreenState.Initial -> {}
            CellScreenState.Loading -> {}
            is CellScreenState.Success -> {
                CellList(cells = currentState.data, modifier = Modifier.weight(1f), listState = listState)
            }
        }

        Spacer(modifier = Modifier.height(36.dp))

        Button(
            onClick = { viewModel.addCell() },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(btnPurple)
        ) {
            Text(
                text = stringResource(id = R.string.create),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

    LaunchedEffect(state.value) {
        if (state.value is CellScreenState.Success) {
            listState.animateScrollToItem((state.value as CellScreenState.Success).data.size - 1)
        }
    }
}

@Composable
fun CellList(cells: List<Cell>, listState: LazyListState, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier, state = listState) {
        items(cells) { cell ->
            when (cell.state) {
                CellState.LIVE -> CellItem(
                    stringResource(id = R.string.live_title),
                    stringResource(id = R.string.live_description),
                    R.drawable.live,
                    listOf(liveImgFirst, liveImgSecond)
                )
                CellState.ALIVE -> CellItem(
                    stringResource(id = R.string.alive_title),
                    stringResource(id = R.string.alive_description),
                    R.drawable.alive,
                    listOf(aliveImgFirst, aliveImgSecond)
                )
                CellState.DEAD -> CellItem(
                    stringResource(id = R.string.dead_title),
                    stringResource(id = R.string.dead_description),
                    R.drawable.dead,
                    listOf(deadImgFirst, deadImgSecond)
                )
            }
        }
    }
}

@Composable
fun CellItem(
    cellTitle: String,
    cellDescription: String,
    cellImg: Int,
    colors: List<Color>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = cellImg),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = colors
                        )
                    )
                    .padding(12.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = cellTitle,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = cellDescription,
                    fontSize = 14.sp,
                )
            }
        }
    }
}
