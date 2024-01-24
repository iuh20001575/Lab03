package vn.edu.vn.iuh.fit;

import opennlp.tools.langdetect.LanguageDetectorME;
import opennlp.tools.langdetect.LanguageDetectorModel;
import opennlp.tools.parser.ParserModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class NLP {
    private static ParserModel model = null;

    public NLP() {
        try (InputStream modelIn = new FileInputStream("en-parser-chunking.bin")) {
            model = new ParserModel(modelIn);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Optional<ParserModel> getParserModel() {
        if (model == null)
            return Optional.empty();

        return Optional.of(model);
    }
}
