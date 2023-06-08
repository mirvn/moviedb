package com.mirvan.moviedb.App_feature.presentation.item

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mirvan.moviedb.App_feature.domain.model.Genres
import com.mirvan.moviedb.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemListGenre(
    modifier: Modifier = Modifier,
    onCLickDetail: () -> Unit = {},
    genre: Genres.Genre
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 4.dp),
        onClick = onCLickDetail,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = modifier
                .wrapContentSize()
                .padding(8.dp)
        ) {
            Text(
                text = genre.name.toString(),
                modifier = modifier
                    .wrapContentSize()
                    .fillMaxWidth(0.9f)
                    .padding(start = 8.dp)
            )
            Icon(
                imageVector = Icons.Rounded.ArrowRight,
                contentDescription = stringResource(id = R.string.icon_genre),
                modifier = modifier.wrapContentSize(align = Alignment.CenterEnd)
            )
        }
    }
}
