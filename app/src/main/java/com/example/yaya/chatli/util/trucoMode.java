package com.example.yaya.chatli.util;

import android.util.Log;

import java.util.ArrayList;

public class trucoMode {

    private static final String TAG = "truco";

// pregunta是指用户输入的文本

    public static ArrayList<String> ArticuleMode(String pregunta) {

        ArrayList<String> urlArtucule = new ArrayList<>();
        pregunta = pregunta.replace(",", " ");
        if (!pregunta.equalsIgnoreCase("")) {
            //String nota = "¿Puedes darme alguna palabra clave complementaria? " + pregunta ;


            String urlScholar = "https://scholar.google.es/scholar?hl=es&as_sdt=0%2C5&q=" + pregunta + "&btnG=";
            String urlDart = "https://dialnet.unirioja.es/buscar/revistas?querysDismax.REVISTA_TODO=" + pregunta +
                    "&__multiselect_querys.REVISTA_MATERIAS=&querysDismax.REVISTA_CODIGO=";
            String urlUcm = "https://ucm.on.worldcat.org/search?lang=es&clusterResults=off&stickyFacetsChecked=on&" +
                    "sortKey=BEST_MATCH&changedFacet=format&subformat=Artchap%3A%3Aartchap_artcl&queryString=" + pregunta;
            String urlElis = "http://eprints.rclis.org/cgi/search/archive/advanced?screen=Search&" +
                    "dataset=archive&documents_merge=ALL&documents=" + pregunta +
                    "&title_merge=ALL&title=&creators_name_merge=ALL&creators_name=" +
                    "&abstract_merge=ALL&abstract=&date=&keywords_merge=ALL" +
                    "&keywords=&subjects_merge=ANY&type=journale&type=journalp&department_merge=ALL&department=" +
                    "&editors_name_merge=ALL&editors_name=&refereed=EITHER&publication_merge=ALL&publication=" +
                    "&linguabib_merge=ANY&satisfyall=ALL&order=-date%2Fcreators_name%2Ftitle&_action_search=Search";

            urlArtucule.add(urlScholar);
            urlArtucule.add(urlDart);
            urlArtucule.add(urlUcm);
            urlArtucule.add(urlElis);

            for (int i = 0; i < urlArtucule.size(); i++) {
                Log.d(TAG, urlArtucule.get(i));

            }
        }
        return urlArtucule;
    }


    public static ArrayList<String> ThesisMode(String pregunta) {

        ArrayList<String> urlArtucule = new ArrayList<>();
        pregunta = pregunta.replace(",", " ");

        String urlDialet = "https://dialnet.unirioja.es/buscar/tesis?querysDismax.DOCUMENTAL_TODO=" + pregunta;
        String urlUcm = "https://ucm.on.worldcat.org/search?queryString=" + pregunta +
                "&changedFacet=database&subscope=sz%3A37628%3A%3Azs%3A37297&l" +
                "ang=es&stickyFacetsChecked=on&clusterResults=off";
        String urlEles = "http://eprints.rclis.org/cgi/search/archive/advanced?screen=Search" +
                "&dataset=archive&documents_merge=ALL&documents=" + pregunta + "&title_merge=ALL" +
                "&title=&creators_name_merge=ALL&creators_name=&abstract_merge=ALL" +
                "&abstract=&date=&keywords_merge=ALL&keywords=&subjects_merge=ANY" +
                "&type=thesis&department_merge=ALL&department=&editors_name_merge=ALL&editors_name=" +
                "&refereed=EITHER&publication_merge=ALL&publication=&linguabib_merge=ANY&satisfyall=" +
                "ALL&order=-date%2Fcreators_name%2Ftitle&_action_search=Search";

        ArrayList<String> urlThesis = new ArrayList<>();

        urlThesis.add(urlDialet);
        urlThesis.add(urlUcm);
        urlThesis.add(urlEles);

        return urlThesis;
    }


    public static ArrayList<String> guideMode(String pregunta) {

        ArrayList<String> urlArtucule = new ArrayList<>();
        pregunta = pregunta.replace(",", " ");

        String urlYale = "https://guides.library.yale.edu/srch.php?q=" + pregunta;
        String urlMcGill = "http://libraryguides.mcgill.ca/srch.php?q=" + pregunta;
        String urlColumbia = "https://clio.columbia.edu/?q=" + pregunta + "&datasource=quicksearch&search_field=all_fields&search=true";
        String urlSyracuse = "https://researchguides.library.syr.edu/srch.php?q=" + pregunta;
        String urlManchester = "https://www.librarysearch.manchester.ac.uk/discovery/search?search_scope=MyInst_and_CI&" +
                "pcAvailability=false&sortby=rank&vid=44MAN_INST:MU_NUI&mode=basic&userQuery=" + pregunta + "&submit=Search&query=any,contains,8888&pfilter=";

        ArrayList<String> urlGuide = new ArrayList<>();

        urlGuide.add(urlYale);
        urlGuide.add(urlMcGill);
        urlGuide.add(urlColumbia);
        urlGuide.add(urlSyracuse);
        urlGuide.add(urlManchester);

        return urlGuide;
    }


}
