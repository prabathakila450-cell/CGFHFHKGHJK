package com.example

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class EssayQuestion(
    val id: String,
    val subject: String,
    val categoryNumber: Int, // 1 to 10
    val questionNumber: Int, // 1 to 10 (Total 100 per subject)
    val title: String,
    val questionText: String,
    val markingScheme: List<String>,
    val modelAnswer: String
)

object ComprehensiveEssayDataBank {
    val subjects = listOf("විද්‍යාව (Science)", "ගණිතය (Mathematics)", "ඉතිහාසය (History)", "පුරවැසි අධ්‍යාපනය (Civics)", "වාණිජ්‍යය (Commerce)")

    private val scienceTopics = listOf(
        "ජීවීන්ගේ විවිධත්වය හා සෛල ව්‍යුහය",
        "පදාර්ථයේ ව්‍යුහය හා පරමාණුක ආකෘති",
        "බලය, චලිතය හා නිව්ටන් නියම",
        "ශාක හා සත්ත්ව පටක වර්ග",
        "ආලෝකය (පරාවර්තනය, වර්තනය හා කාච)",
        "රසායනික බන්ධන හා මවුල සංකල්පය",
        "ප්‍රභාසංශ්ලේෂණය සහ ශාක පෝෂණය",
        "ධාරා විද්‍යුතය සහ ඕම්ගේ නියමය",
        "මිනිසාගේ ශරීර පද්ධති (ශ්වසන, රුධිර සංසරණ)",
        "පරිසරය, බලශක්තිය හා තිරසාර සංවර්ධනය"
    )

    private val mathsTopics = listOf(
        "භාග, දශම හා ප්‍රතිශත ගැටලු",
        "බීජීය ප්‍රකාශන ප්‍රසාරණය හා සාධක",
        "රේඛීය සමීකරණ හා සමගාමී සමීකරණ",
        "පරිමිතිය, වර්ගඵලය සහ ජ්‍යාමිතික මිනුම්",
        "දර්ශක හා ලඝුගණක නීති",
        "වර්ගජ සමීකරණ විසඳීම",
        "ත්‍රිකෝණමිතිය (උන්නතාංශ හා අවනතාංශ කෝණ)",
        "වෘත්ත ප්‍රමේය හා ජ්‍යාමිතික කෝණ",
        "සංඛ්‍යානය (මධ්‍යන්‍යය, මාතය, මධ්‍යස්ථය)",
        "සම්භාවිතාව හා කුලක සංකල්ප"
    )

    private val historyTopics = listOf(
        "ඉතිහාසය හැදෑරීමේ පුරාවිද්‍යා හා සාහිත්‍ය මූලාශ්‍ර",
        "ශ්‍රී ලංකාවේ ප්‍රාග් ඓතිහාසික මානව ජනාවාස",
        "අනුරාධපුර රාජධානියේ වාරි තාක්ෂණය හා වැව් පද්ධති",
        "බුදුදහම මෙරටට පැමිණීම හා සංස්කෘතික පිබිදීම",
        "දුටුගැමුණු, වළගම්බා හා ධාතුසේන රජවරුන්ගේ සේවය",
        "පොළොන්නරු යුගයේ මහා පරාක්‍රමබාහු රාජ්‍ය සමය",
        "දඹදෙණිය, යාපහුව සහ ගම්පොළ රාජධානි",
        "කෝට්ටේ යුගයේ සාහිත්‍ය හා විදේශීය ආගමනය",
        "උඩරට රාජධානිය සහ 1815 උඩරට ගිවිසුම",
        "1818 සහ 1848 නිදහස් සටන් හා ජාතික පුනරුදය"
    )

    private val civicsTopics = listOf(
        "ප්‍රජාතන්ත්‍රවාදී පාලන ක්‍රමය හා මූලධර්ම",
        "ශ්‍රී ලංකා ආණ්ඩුක්‍රම ව්‍යවස්ථාව හා රාජ්‍ය බලය",
        "මූලික මිනිස් අයිතිවාසිකම් සහ නීතියේ ආධිපත්‍යය",
        "පළාත් පාලන ආයතන හා ප්‍රාදේශීය පරිපාලනය",
        "රාජ්‍ය මූල්‍ය කළමනාකරණය හා අයවැය ක්‍රියාවලිය",
        "ජාත්‍යන්තර සංಘಟನೆ සහ එක්සත් ජාතීන්ගේ සංවිධානය",
        "පාරිභෝගික අයිතිවාසිකම් හා පාරිභෝගික ආරක්ෂණය",
        "සාමකාමී සහජීවනය හා ගැටලු නිරාකරණය",
        "යහපාලනය, විනිවිදභාවය සහ වගවීම",
        "තිරසාර සංවර්ධනය හා පරිසර සංරක්ෂණය"
    )

    private val commerceTopics = listOf(
        "ව්‍යාපාරයක මූලික සංකල්ප හා අරමුණු",
        "வணික පරිසරය හා ව්‍යාපාර වර්ගීකරණය",
        "ද්වි-නාම ක්‍රමය හා මූලික ගිණුම්කරණ සමීකරණය",
        "ජර්නලය, ලෙජරය සහ ශේෂ පත්‍ර සකස් කිරීම",
        "බැංකු කටයුතු හා මූල්‍ය සේවා ආයතන",
        "අලෙවිකරණය (Marketing) සහ වෙළඳ දැන්වීම්",
        "මානව සම්පත් කළමනාකරණය හා වැඩබිම් සංවිධානය",
        "ආනයන හා අපනයන වෙළඳ ක්‍රියාවලි",
        "මූල්‍ය ප්‍රකාශන (ලාභ අලාභ ගිණුම සහ ශේෂ පත්‍රය)",
        "ව්‍යාපාර සැලසුම්කරණය හා ව්‍යවසායකත්වය"
    )

    fun getQuestionsForSubjectAndCategory(subject: String, category: Int): List<EssayQuestion> {
        val list = mutableListOf<EssayQuestion>()
        val topicList = when {
            subject.contains("විද්‍යාව") -> scienceTopics
            subject.contains("ගණිතය") -> mathsTopics
            subject.contains("ඉතිහාසය") -> historyTopics
            subject.contains("පුරවැසි") -> civicsTopics
            else -> commerceTopics
        }
        val topicName = topicList.getOrElse(category - 1) { "O/L විභාග පුහුණු ඒකකය" }

        for (qNum in 1..10) {
            val globalIndex = (category - 1) * 10 + qNum
            val questionTitle = "ප්‍රශ්න අංක $globalIndex: $topicName (කොටස $qNum)"
            
            val (qText, markingScheme, modelAnswer) = when {
                subject.contains("විද්‍යාව") -> {
                    val text = "ශ්‍රී ලංකාවේ O/L විභාග ප්‍රශ්න පත්‍රයේ II කොටස සඳහා අදාළ වන පරිදි '$topicName' (උප ප්‍රශ්න අංක $qNum) යටතේ එන ව්‍යුහගත ගැටළුවකි. මෙහිදී අදාළ විද්‍යාත්මක සිද්ධාන්ත, පර්යේෂණ ඇටවුම් හෝ රසායනික සමීකරණ පියවරෙන් පියවර පැහැදිලි කරන්න."
                    val ms = listOf(
                        "🟢 (i) මූලික විද්‍යාත්මක සංකල්පය හඳුනාගැනීම හා නිරවද්‍ය පාරිභාෂික යෙදුම් භාවිතය - ලකුණු 03",
                        "🔵 (ii) අදාළ සමීකරණ, ප්‍රායෝගික ඇටවුම් හෝ විචල්‍යයන් නිවැරදිව නම් කිරීම - ලකුණු 03",
                        "🟠 (iii) ක්‍රමික තාර්කික විශ්ලේෂණය සහ දත්ත සැකසීම - ලකුණු 02",
                        "🟣 (iv) නිගමනය සහ SI ඒකක සහිත අවසන් ප්‍රතිඵලය දැක්වීම - ලකුණු 02"
                    )
                    val ans = "✨ [විද්‍යා O/L නිල ආදර්ශ පිළිතුර & ලකුණු දීමේ ක්‍රියාපටිපාටිය - ප්‍රශ්න අංක $globalIndex]\n\n" +
                        "1. (i) හැඳින්වීම හා නිර්වචනය:\n   • '$topicName' යටතේ ප්‍රධාන විද්‍යාත්මක මූලධර්මය නිරවුල්ව දක්වා ඇත (ලකුණු 3).\n\n" +
                        "2. (ii) විද්‍යාත්මක සිද්ධාන්ත හා පර්යේෂණ පියවර:\n   • පර්යේෂණයේදී පාලිත විචල්‍යයන් නියතව තබා ස්වායත්ත විචල්‍යය වෙනස් කිරීමේ ක්‍රමවේදය පැහැදිලි කර ඇත (ලකුණු 3).\n\n" +
                        "3. (iii) ගණනය කිරීම් හා දත්ත විශ්ලේෂණය:\n   • අදාළ සූත්‍ර හෝ රසායනික සමීකරණ තුල්‍ය කර පියවරෙන් පියවර විසඳා ඇත (ලකුණු 2).\n\n" +
                        "4. (iv) අවසන් නිගමනය:\n   • ප්‍රතිඵලය භෞතික/ජෛවීය සංසිද්ධි සමඟ සසඳා නිගමනය කර ඇත (ලකුණු 2)."
                    Triple(text, ms, ans)
                }
                subject.contains("ගණිතය") -> {
                    val text = "O/L ගණිතය පත්‍රයේ II කොටසේ '$topicName' (අංක $qNum) යටතේ එන ගැටළුවක් සඳහා න්‍යායාත්මක සූත්‍ර හා ක්‍රමික පියවර අනුගමනය කරමින් සම්පූර්ණ පිළිතුර ලබාදෙන්න."
                    val ms = listOf(
                        "🟢 (i) ගැටළුවේ දත්ත ලැයිස්තුගත කිරීම හා ගැළපෙන සූත්‍රය තෝරාගැනීම - ලකුණු 03",
                        "🔵 (ii) සංඛ්‍යාත්මක අගයන් නිවැරදිව සූත්‍රයට ආදේශ කිරීම - ලකුණු 03",
                        "🟠 (iii) අනුක්‍රමිකව සුළු කිරීමේ පියවර නිවැරදිව පෙන්වීම - ලකුණු 02",
                        "🟣 (iv) දශමස්ථාන සහ සම්මත ඒකක සහිත අවසන් නිවැරදි අගය ලබාගැනීම - ලකුණු 02"
                    )
                    val ans = "✨ [ගණිතය O/L නිල ආදර්ශ පිළිතුර & ලකුණු දීමේ ක්‍රියාපටිපාටිය - ප්‍රශ්න අංක $globalIndex]\n\n" +
                        "1. (i) දත්ත හා සූත්‍ර තෝරාගැනීම:\n   • ප්‍රශ්නයේ දී ඇති දත්ත සංකේත මඟින් දක්වා නිවැරදි ගණිතමය සූත්‍රය ලියා ඇත (ලකුණු 3).\n\n" +
                        "2. (ii) ආදේශනය හා සුළු කිරීම:\n   • සූත්‍රයට අගයන් ආදේශ කර පියවරෙන් පියවර ගණනය කර ඇත (ලකුණු 3).\n\n" +
                        "3. (iii) පියවර ප්‍රදර්ශනය:\n   • ජ්‍යාමිතික ප්‍රමේය හෝ වීජීය සාධක නිවැරදිව යොදාගෙන ඇත (ලකුණු 2).\n\n" +
                        "4. (iv) අවසන් පිළිතුර:\n   • නිවැරදි සංඛ්‍යාත්මක අගය සහ ඒකක (සෙ.මී., වර්ග සෙ.මී., මීටර් ආදී) පැහැදිලිව දක්වා ඇත (ලකුණු 2)."
                    Triple(text, ms, ans)
                }
                subject.contains("ඉතිහාසය") -> {
                    val text = "'$topicName' (ප්‍රශ්න කොටස $qNum) යන ඓතිහාසික පසුබිම හා මූලාශ්‍ර පදනම් කරගනිමින්, අදාළ ඓතිහාසික සාධක, රාජ්‍යතාන්ත්‍රික ක්‍රියාමාර්ග සහ සමාජ-ආර්ථික බලපෑම් විශ්ලේෂණය කර රචනාත්මකව පිළිතුරු සපයන්න."
                    val ms = listOf(
                        "🟢 (i) ඓතිහාසික කාල වකවානුව හා ප්‍රධාන මූලාශ්‍ර නිවැරදිව හැඳින්වීම - ලකුණු 03",
                        "🔵 (ii) අදාළ රජවරුන්, නායකයන් හෝ සංස්කෘතික සංවර්ධනයන් විස්තර කිරීම - ලකුණු 03",
                        "🟠 (iii) දේශපාලනික හා ආර්ථික වැදගත්කම ගැඹුරින් විශ්ලේෂණය කිරීම - ලකුණු 02",
                        "🟣 (iv) සමස්ත ඓතිහාසික යුගයේ සුවිශේෂී ප්‍රවේශය සහ නිගමනය - ලකුණු 02"
                    )
                    val ans = "✨ [ඉතිහාසය O/L නිල ආදර්ශ පිළිතුර & ලකුණු දීමේ ක්‍රියාපටිපාටිය - ප්‍රශ්න අංක $globalIndex]\n\n" +
                        "1. (i) හැඳින්වීම:\n   • ඓතිහාසික පසුබිම, පුරාවිද්‍යා හෝ සාහිත්‍ය මූලාශ්‍ර සඳහන් කර ඇත (ලකුණු 3).\n\n" +
                        "2. (ii) ප්‍රධාන කරුණු විග්‍රහය:\n   • වාරි කර්මාන්ත, දාගැබ් නිර්මාණ හෝ රාජ්‍ය සංවිධාන ක්‍රමවේද විස්තර කර ඇත (ලකුණු 3).\n\n" +
                        "3. (iii) සමාජීය හා ආර්ථික බලපෑම:\n   • එම යුගයේ ජනජීවිතය සහ අන්තර්ජාතික සබඳතා සාකච්ඡා කර ඇත (ලකුණු 2).\n\n" +
                        "4. (iv) නිගමනය:\n   • ශ්‍රී ලංකා ඉතිහාසයේ එම යුගයේ සදාස්මරණීය වැදගත්කම සාරාංශ කර ඇත (ලකුණු 2)."
                    Triple(text, ms, ans)
                }
                subject.contains("පුරවැසි") -> {
                    val text = "'$topicName' (කොටස $qNum) යටතේ ප්‍රජාතන්ත්‍රවාදී වටිනාකම්, ආණ්ඩුක්‍රම ව්‍යවස්ථා ප්‍රතිපාදන, නීතියේ ආධිපත්‍යය හෝ පුරවැසි අයිතිවාසිකම් හා වගකීම් පිළිබඳව විමසා තර්කානුකූලව පිළිතුරු ලියන්න."
                    val ms = listOf(
                        "🟢 (i) මූලික සංකල්ප හා ප්‍රජාතන්ත්‍රවාදී මූලධර්ම නිවැරදිව නම් කිරීම - ලකුණු 03",
                        "🔵 (ii) ව්‍යවස්ථාපිත නීතිමය රාමුව හෝ ආයතනික ක්‍රියාකාරීත්වය පැහැදිලි කිරීම - ලකුණු 03",
                        "🟠 (iii) පුරවැසි කාර්යභාරය හා ප්‍රායෝගික සමාජ ගැටලු විසඳීමේ ක්‍රම - ලකුණු 02",
                        "🟣 (iv) යහපාලන මූලධර්ම මත පදනම් වූ සංගත නිගමනය - ලකුණු 02"
                    )
                    val ans = "✨ [පුරවැසි අධ්‍යාපනය O/L නිල ආදර්ශ පිළිතුර & ලකුණු දීමේ ක්‍රියාපටිපාටිය - ප්‍රශ්න අංක $globalIndex]\n\n" +
                        "1. (i) සංකල්පීය හැඳින්වීම:\n   • අදාළ මාතෘකාවට අදාළ ප්‍රජාතන්ත්‍රවාදී මූලධර්මය පැහැදිලි කර ඇත (ලකුණු 3).\n\n" +
                        "2. (ii) නීතිමය හා ව්‍යවස්ථාදායක රාමුව:\n   • ආණ්ඩුක්‍රම ව්‍යවස්ථාවේ විධිවිධාන හෝ ආයතනවල වගවීම සාකච්ඡා කර ඇත (ලකුණු 3).\n\n" +
                        "3. (iii) ප්‍රායෝගික යෙදුම:\n   • පුරවැසියකු ලෙස සමාජ සංවර්ධනයට දායක වන ආකාරය දක්වා ඇත (ලකුණු 2).\n\n" +
                        "4. (iv) නිගමනය:\n   • නීතියේ ආධිපත්‍යය සහ සාමකාමී සමාජයක් පිළිබඳ අදහස ඉදිරිපත් කර ඇත (ලකුණු 2)."
                    Triple(text, ms, ans)
                }
                else -> {
                    val text = "'$topicName' (කොටස $qNum) විෂය ක්ෂේත්‍රයට අදාළව ව්‍යාපාරික පරිසරය, මූල්‍ය වාර්තාකරණය, ද්වි-නාම ක්‍රමය හෝ කළමනාකරණ ක්‍රියාවලිය විශ්ලේෂණය කර නිවැරදි ගිණුම්කරණ හෝ ව්‍යාපාරික විසඳුම සපයන්න."
                    val ms = listOf(
                        "🟢 (i) ව්‍යාපාරික සංකල්පය හෝ ගිණුම්කරණ සමීකරණය හඳුනාගැනීම - ලකුණු 03",
                        "🔵 (ii) ජර්නල් සටහන්, ලෙජර හෝ මූල්‍ය ප්‍රකාශන නිවැරදිව සකස් කිරීම - ලකුණු 03",
                        "🟠 (iii) ලාභ අලාභ ගිණුම හෝ ශේෂ පත්‍රය තුල්‍ය කිරීම - ලකුණු 02",
                        "🟣 (iv) ව්‍යාපාරික තීන්දු ගැනීමේදී මූල්‍ය දත්ත අර්ථකථනය කිරීම - ලකුණු 02"
                    )
                    val ans = "✨ [වාණිජ්‍යය O/L නිල ආදර්ශ පිළිතුර & ලකුණු දීමේ ක්‍රියාපටිපාටිය - ප්‍රශ්න අංක $globalIndex]\n\n" +
                        "1. (i) මූලික ගිණුම්කරණ මූලධර්ම:\n   • ව්‍යාපාරික ගනුදෙනු විශ්ලේෂණය කර අදාළ ගිණුම් වර්ග කර ඇත (ලකුණු 3).\n\n" +
                        "2. (ii) ප්‍රවේශය හා සටහන් කිරීම:\n   • හර සහ බැරු පැති නිවැරදිව ගළපා ජර්නල් හෝ ලෙජර් සටහන් කර ඇත (ලකුණු 3).\n\n" +
                        "3. (iii) මූල්‍ය වාර්තාකරණය:\n   • ඊට අදාළ මූල්‍ය ප්‍රකාශනවල නිවැරදි ශේෂයන් දක්වා ඇත (ලකුණු 2).\n\n" +
                        "4. (iv) ව්‍යාපාරික නිගමනය:\n   • ව්‍යාපාරයේ මූල්‍ය තත්ත්වය සහ ලාභදායීතාවය පිළිබඳ නිගමනය කර ඇත (ලකුණු 2)."
                    Triple(text, ms, ans)
                }
            }

            list.add(
                EssayQuestion(
                    id = "${subject}_c${category}_q${qNum}",
                    subject = subject,
                    categoryNumber = category,
                    questionNumber = qNum,
                    title = questionTitle,
                    questionText = qText,
                    markingScheme = markingScheme,
                    modelAnswer = modelAnswer
                )
            )
        }
        return list
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StructuredEssayAssessmentHub(onBack: () -> Unit) {
    var selectedSubject by remember { mutableStateOf(ComprehensiveEssayDataBank.subjects[0]) }
    var selectedCategory by remember { mutableStateOf(1) } // 1 to 10
    var selectedQuestion by remember { mutableStateOf<EssayQuestion?>(null) }
    var studentAnswer by remember { mutableStateOf("") }
    var showMarkingScheme by remember { mutableStateOf(false) }

    val currentQuestions = remember(selectedSubject, selectedCategory) {
        ComprehensiveEssayDataBank.getQuestionsForSubjectAndCategory(selectedSubject, selectedCategory)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("රචනා පුහුණුව • ප්‍රශ්න 100 (කාණ්ඩ 10)", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = {
                        if (selectedQuestion != null) {
                            selectedQuestion = null
                            showMarkingScheme = false
                            studentAnswer = ""
                        } else {
                            onBack()
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0F172A))
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize().background(Color(0xFFF8FAFC))) {
            if (selectedQuestion == null) {
                Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    Text("විෂයක් සහ කාණ්ඩයක් (සෑම කාණ්ඩයකටම ප්‍රශ්න 10 බැගින්, විෂයකට මුළු ප්‍රශ්න 100ක්) තෝරන්න:", fontSize = 12.5.sp, color = Color(0xFF475569), fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(10.dp))

                    // Subject Selector
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ComprehensiveEssayDataBank.subjects.forEach { subj ->
                            FilterChip(
                                selected = selectedSubject == subj,
                                onClick = { selectedSubject = subj },
                                label = { Text(subj, fontSize = 11.5.sp, fontWeight = FontWeight.Bold) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color(0xFF0284C7),
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Category Selector (1 to 10)
                    Text("අධ්‍යයන කාණ්ඩය (Category 1 - 10):", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        for (cat in 1..10) {
                            FilterChip(
                                selected = selectedCategory == cat,
                                onClick = { selectedCategory = cat },
                                label = { Text("කාණ්ඩය $cat", fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color(0xFF10B981),
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFEFF6FF),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFBFDBFE)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "📚 $selectedSubject • කාණ්ඩය $selectedCategory (ප්‍රශ්න 10)",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1D4ED8),
                            fontSize = 12.5.sp,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    LazyColumn(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(currentQuestions) { q ->
                            Card(
                                modifier = Modifier.fillMaxWidth().clickable { selectedQuestion = q },
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Surface(
                                            shape = RoundedCornerShape(4.dp),
                                            color = Color(0xFFFEF3C7)
                                        ) {
                                            Text(
                                                text = "ප්‍රශ්න අංක ${q.questionNumber}",
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFFB45309),
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                        Text("මුළු ලකුණු: 10", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF059669))
                                    }
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(q.title, fontSize = 14.5.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(q.questionText, fontSize = 12.5.sp, color = Color(0xFF475569), maxLines = 2)
                                }
                            }
                        }
                    }
                }
            } else {
                val q = selectedQuestion!!
                LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    item {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFFE0F2FE)
                        ) {
                            Text(q.subject, fontSize = 11.sp, color = Color(0xFF0284C7), fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(q.title, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                        Spacer(modifier = Modifier.height(8.dp))
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFCBD5E1))
                        ) {
                            Text(q.questionText, modifier = Modifier.padding(16.dp), fontSize = 14.sp, color = Color(0xFF1E293B), lineHeight = 20.sp)
                        }
                    }
                    item {
                        Text("ඔබේ පිළිතුර මෙහි සටහන් කරන්න (Student Scratchpad):", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF334155))
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                            value = studentAnswer,
                            onValueChange = { studentAnswer = it },
                            modifier = Modifier.fillMaxWidth().height(150.dp),
                            placeholder = { Text("මෙහි පිළිතුර ලියන්න...") },
                            shape = RoundedCornerShape(10.dp)
                        )
                    }
                    item {
                        Button(
                            onClick = { showMarkingScheme = true },
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7)),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.RateReview, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("නිල ලකුණු දීමේ ක්‍රියාපටිපාටිය සහ ආදර්ශ පිළිතුර බලන්න", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                    if (showMarkingScheme) {
                        item {
                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
                                border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF86EFAC))
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF16A34A), modifier = Modifier.size(20.dp))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("ලකුණු ලබා දීමේ ක්‍රියාපටිපාටිය (Marking Scheme & Rubric):", fontWeight = FontWeight.Bold, fontSize = 13.5.sp, color = Color(0xFF166534))
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    q.markingScheme.forEach { rule ->
                                        Text("• $rule", fontSize = 13.sp, color = Color(0xFF14532D), fontWeight = FontWeight.Medium, modifier = Modifier.padding(vertical = 2.dp))
                                    }
                                    Spacer(modifier = Modifier.height(14.dp))
                                    HorizontalDivider(color = Color(0xFFBBF7D0))
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text("ආදර්ශ පිළිතුර (Model Answer):", fontWeight = FontWeight.Bold, fontSize = 13.5.sp, color = Color(0xFF166534))
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(q.modelAnswer, fontSize = 13.sp, color = Color(0xFF14532D), lineHeight = 19.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
