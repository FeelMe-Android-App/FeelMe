import com.feelme.feelmeapp.R

class Feeling (val icon: Int, val name: String)

val moodList: List<Feeling> = listOf(
        Feeling(R.drawable.ic_impressed, "Impressionado"),
        Feeling(R.drawable.ic_comedy, "Engraçado"),
        Feeling(R.drawable.ic_sad, "Triste"),
        Feeling(R.drawable.ic_shocked, "Chocado"),
        Feeling(R.drawable.ic_afflicted, "Aflito"),
    )