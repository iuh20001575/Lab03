package vn.edu.vn.iuh.fit;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.validator.language_level_validations.chunks.CommonValidators;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.langdetect.Language;
import opennlp.tools.langdetect.LanguageDetectorME;
import opennlp.tools.langdetect.LanguageDetectorModel;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;

import java.io.*;
import java.util.Optional;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        File projectDir = new File("T:\\KT\\WWW_Week07");

        DirExplorer.FileHandler fileHandler = (level, path, file) -> {
            try {
                new VoidVisitorAdapter<>() {
                    @Override
                    public void visit(PackageDeclaration n, Object arg) {
                        super.visit(n, arg);
                        checkPackage(n);
                    }

                    @Override
                    public void visit(ClassOrInterfaceDeclaration n, Object arg) {
                        super.visit(n, arg);

                        checkClassName(n);
                    }
                }.visit(StaticJavaParser.parse(file), null);
            } catch (Exception e) {
                System.err.println(e);
            }
        };

        DirExplorer.Filter filter = (level, path, file) -> file.getName().endsWith(".java");

        new DirExplorer(fileHandler, filter).explore(projectDir);
    }

    private static void checkPackage(PackageDeclaration n) {
        String packageName = n.getNameAsString();
        String packageStart = "vn.edu.iuh.fit.backend";

        if (!packageName.startsWith(packageStart)) {
            System.out.printf("Package %s isn't start with %s\n", packageName, packageStart);
        }
    }

    private static void checkClassName(ClassOrInterfaceDeclaration n) {
        Optional<ParserModel> oParserModel = NLP.getParserModel();
        System.out.println(oParserModel.isEmpty());

        if (oParserModel.isEmpty()) return;

        ParserModel parserModel = oParserModel.get();
        Parser parser = ParserFactory.create(parserModel);

        String sentence = "The quick brown fox jumps over the lazy dog .";
        Parse topParses[] = ParserTool.parseLine(sentence, parser, 1);

        System.out.println(topParses);
    }
}