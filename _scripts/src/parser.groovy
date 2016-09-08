
@Grapes( @Grab('com.xlson.groovycsv:groovycsv:1.0') )
import com.xlson.groovycsv.*
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.regex.Pattern;
import groovy.io.FileType

def dir = new File("categories")
dir.eachFileRecurse (FileType.FILES) { csvFile ->

    println "Scraping ${csvFile.name}..."

    def data = new CsvParser().parse(new FileReader(csvFile))
    List productList = []

    data.each { row ->
        Map product = [:]
        row.columns.each{ key, value ->
            product[key] = row[key]
        }
        productList << product
    }

        int counter = 1, sectionRank = 0
        productList.eachWithIndex { product, index ->
//        counter = counter+1
        sectionRank = sectionRank+1
        String type = "corporate"

        "mkdir -p out/".execute()
        File file = new File("out/" + slugify(product.title) +'.md')
        println "out/" +slugify(product.title) +'.md'
        String imageLink = product.link.replaceAll("\\/album\\/([\\d]+)","https://player.vimeo.com")
        String imageLarge = product.image.replace('295x166', '1920x700')
        String imageSmall = product.image.replace('295x166', '750x500')
        file.write ('---\n')

//        file << "slugID: $counter \n"
        file << "layout: reel\n"
        file << "title: \"$product.title\"\n"
        file << "image-large: \"$imageLarge\"\n"
        file << "image-small: \"$imageSmall\"\n"
        file << "link: \"$imageLink\"\n"
        file << "type: \"$type\"\n"
        file << "section-rank: $sectionRank\n"
        file << '---' << '\n'
    }
}

public String slugify (String toBeSlugged){
    Pattern NONLATIN = Pattern.compile("[^\\w-]")
    Pattern WHITESPACE = Pattern.compile("[\\s]")

    String nowhitespace = WHITESPACE.matcher(toBeSlugged.replaceAll(" - ", " ")).replaceAll("-")
    String normalized = Normalizer.normalize(nowhitespace, Form.NFD)
    String slug = NONLATIN.matcher(normalized).replaceAll("")
    return slug.toLowerCase(Locale.ENGLISH)
}

