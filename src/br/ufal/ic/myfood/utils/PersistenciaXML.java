package br.ufal.ic.myfood.utils;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.beans.ExceptionListener;

public class PersistenciaXML {

    public static void salvar(Object objeto, String caminhoArquivo) {
        try {
            File arquivo = new File(caminhoArquivo);

            File diretorio = arquivo.getParentFile();

            if (diretorio != null && !diretorio.exists()) {
                diretorio.mkdirs();
            }

            try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(arquivo)))) {


                encoder.setExceptionListener(new java.beans.ExceptionListener() {
                    @Override
                    public void exceptionThrown(Exception e) {
                        System.err.println("O XML ACHOU UM CULPADO: " + e.getMessage());
                        // e.printStackTrace(); // Descomente isso se quiser ver a linha exata do erro
                    }
                });
                // -----------------------

                encoder.writeObject(objeto);
            }

        } catch (FileNotFoundException e) {
            System.err.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }

    public static Object carregar(String nomeArquivo) {
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(nomeArquivo)))) {
            return decoder.readObject();
        } catch (FileNotFoundException e) {
            return null;
        }
    }
}
