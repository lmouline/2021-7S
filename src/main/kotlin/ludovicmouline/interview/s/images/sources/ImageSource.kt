package ludovicmouline.interview.s.images.sources

import ludovicmouline.interview.s.images.Image

interface ImageSource {
    fun pullImages(nb: Int) : Array<Image>
}