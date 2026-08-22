package com.helptrickbd.class1.feature.karchihno.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helptrickbd.class1.feature.karchihno.domain.model.KarChihnoItem

@Composable
fun KarChihnoBanner(
    item: KarChihnoItem?,
    isSpeaking: Boolean,
    isSpellingPlaying: Boolean,
    onPlaySign: () -> Unit,
    onPlaySpell: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (item == null) return

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFF1E2538),
        border = BorderStroke(1.dp, Color(0xFF333E54)),
        shadowElevation = 6.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = item.displaySign,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFFFFD54F)
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = "${item.name} (${item.vowelForm})",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "স্বরবর্ণ '${item.fullVowel}' এর কার-চিহ্ন রূপ",
                            fontSize = 12.sp,
                            color = Color(0xFF94A3B8)
                        )
                    }
                }

                IconButton(
                    onClick = onPlaySign,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = if (isSpeaking) Color(0xFFFFD54F) else Color(0xFF283349),
                        contentColor = if (isSpeaking) Color.Black else Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = "কার-চিহ্ন শুনুন"
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(color = Color(0xFF333E54))
            Spacer(modifier = Modifier.height(14.dp))

            // Example Word Box with Tap to Spell
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Color(0xFF252E42),
                border = BorderStroke(1.dp, Color(0xFF3B4863)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onPlaySpell)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.RecordVoiceOver,
                        contentDescription = null,
                        tint = if (isSpellingPlaying) Color(0xFFFFD54F) else Color(0xFF38BDF8),
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "উদাহরণ শব্দ: ",
                                fontSize = 13.sp,
                                color = Color(0xFF94A3B8)
                            )
                            Text(
                                text = item.exampleWord,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFFD54F)
                            )
                        }
                        Text(
                            text = item.spellSentence,
                            fontSize = 12.sp,
                            color = Color(0xFFE2E8F0),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}
