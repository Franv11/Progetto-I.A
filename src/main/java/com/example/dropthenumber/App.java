package com.example.dropthenumber;

import com.example.dropthenumber.controller.MapPossibleBlockArrayMatrix;
import com.example.dropthenumber.model.Block;
import com.example.dropthenumber.model.ExistingBlock;
import com.example.dropthenumber.model.newBlock;
import com.example.dropthenumber.utils.randBlockValue;
import com.example.dropthenumber.view.Graphic;
import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.languages.IllegalAnnotationException;
import it.unical.mat.embasp.languages.ObjectNotValidException;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.*;

public class App extends Application implements EventHandler<ActionEvent> {

    // Costanti per le dimensioni della matrice
    public static final int ROW_SIZE = 6;
    public static final int COL_SIZE = 5;

    //STOP
    private static boolean running = true;

    //HANDLE
    private static Handler handler;

    //FACT e RULE
    InputProgram variableProgram = new ASPInputProgram();
    InputProgram encoding = new ASPInputProgram();

    // Classe generazione numero casuale
    randBlockValue randValue = new randBlockValue();

    // Classe responsabile della grafica
    Graphic matrixFx;

    // Classe responsabile della logica e della generazione delle mosse
    MapPossibleBlockArrayMatrix map;



    @Override
    public void handle(ActionEvent event) {
        if(!running) {
            System.exit(0);
        }else{

            // Calcolo delle mosse possibili e delle matrici successive alla mossa scelta
            map.startClass(matrixFx.getMatrix());

            // Aggiunta fatti , sia blocchi già esistenti che mosse possibili
            addPossibleBlockToProgram(variableProgram, map);
            addExistingBlock(variableProgram);
            handler.addProgram(variableProgram);

            AnswerSets answerSets = (AnswerSets) handler.startSync();
            AnswerSet optimal = new AnswerSet(null);

            try {

                // Get dell'A.S ottimo
                optimal = answerSets.getOptimalAnswerSets().get(0);

                try {
                    for (Object obj : optimal.getAtoms()) {
                        if (obj instanceof newBlock) {
                            newBlock b = (newBlock) obj;

                            // Creazione di un block ausiliario per la stampa del vettore di  matrici contenente tutte le  mosse successive
                            int[] chosenBlock = new int[]{b.getRow(),b.getColumn(),b.getValue()};

                            // Aggiornamento del punteggio e aggiunta del nuovo valore all'array della generazione di numeri rand
                            matrixFx.updateScoreLabel(map.getScoreForBlockChosen(chosenBlock));
                            randValue.addValueToArray(map.returnFinalMatrix(chosenBlock));

                            // Funzione responsabile della visualizzazione del vettore di  matrici del block scelto
                            showAllTheMove(map.getArrayOfMatrixForABlock(chosenBlock));
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                running = false;
                matrixFx.gameOver();
            }

            //Pulizia dei fatti e delle strutture dati
            variableProgram.clearAll();
            map.clearDataStructure();
        }
    }


    public void showAllTheMove(ArrayList<int[][]> moves) {
        Timeline timeline = new Timeline();
        int delay = 0;

        for (int[][] move : moves) {
            KeyFrame keyFrame = new KeyFrame(Duration.millis(delay), e -> {
                updateLabelsWithMove(move);
            });
            timeline.getKeyFrames().add(keyFrame);
            delay += 300;
        }
        timeline.play();
    }

    private void updateLabelsWithMove(int[][] move) {
        for (int i = 0; i < move.length; i++) {
            for (int j = 0; j < move[i].length; j++) {
                if (move[i][j] != 0) {
                    matrixFx.setCell(i , j , move[i][j]);
                } else {
                    matrixFx.setCell(i , j , 0);
                }
            }
        }
    }




    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {

        // Crea handler
        createHandler();

        // inizializzare la scena
        matrixFx = new Graphic(primaryStage);
        // Inizializzare la classe che conserva e calcola le possibili mosse
        map = new MapPossibleBlockArrayMatrix();

        // Registra classi
        registerClass();

        // Aggiunta regole
        encoding.addFilesPath("encodings/drop");
        handler.addProgram(encoding);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3.4), this));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }





    private static void createHandler() {
        handler = new DesktopHandler(new DLV2DesktopService("lib/dlv2.exe"));
    }

    private void registerClass() {
        try {
            ASPMapper.getInstance().registerClass(Block.class);
            ASPMapper.getInstance().registerClass(newBlock.class);
            ASPMapper.getInstance().registerClass(ExistingBlock.class);
        } catch (ObjectNotValidException | IllegalAnnotationException e1) {
            e1.printStackTrace();
        }
    }

    public void addPossibleBlockToProgram(InputProgram facts, MapPossibleBlockArrayMatrix map)
    {
        Set<int[]> possBlocks = map.getPossibleBlock();

        for(var pB : possBlocks ) {
            try {
                facts.addObjectInput(new Block( pB[0] , pB[1] , pB[2] , map.getPossibleBlockAndScore(pB) , map.findIfMatch(pB)));

            } catch (Exception e) {
                System.err.println("ERROR ADDING FACTS TO PROGRAM");
                e.printStackTrace();
            }
        }
    }

    public void addExistingBlock(InputProgram facts) {
        int[][] currentMatrix = matrixFx.getMatrix();
        for(int i = 0; i < ROW_SIZE; i++) {
            for(int j = 0; j < COL_SIZE; j++){
                if(currentMatrix[i][j] != 0) {
                    try {
                        facts.addObjectInput(new ExistingBlock(i, j, currentMatrix[i][j]));
                    } catch (Exception e) {
                        System.err.println("ERROR ADDING EXISTING BLOCK TO PROGRAM");
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}