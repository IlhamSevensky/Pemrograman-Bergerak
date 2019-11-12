package com.example.pemrograman_bergerak_uts.Data;


import com.example.pemrograman_bergerak_uts.Model.Model_Destinasi;

import java.util.ArrayList;

public class DataDestinasi {

    public static String[][] data = new String[][]{
            {"Maldives",
                    "The Maldives, officially the Republic of Maldives, is a small country in South Asia, located in the Arabian Sea of the Indian Ocean. It lies southwest of Sri Lanka and India, about 1,000 kilometres from the Asian continent.",
                    "https://www.costacruises.com/content/dam/costa/inventory-assets/countries/MDV/MDV.jpg.image.750.563.low.jpg"},
            {"Paris",
                    "Paris, France's capital, is a major European city and a global center for art, fashion, gastronomy and culture. Its 19th-century cityscape is crisscrossed by wide boulevards and the River Seine. Beyond such landmarks as the Eiffel Tower and the 12th-century, Gothic Notre-Dame cathedral, the city is known for its cafe culture and designer boutiques along the Rue du Faubourg Saint-Honoré.",
                    "https://www.fodors.com/wp-content/uploads/2018/10/HERO_UltimateParis_Heroshutterstock_112137761.jpg"},
            {"Bali",
                    "Bali is an Indonesian island known for its forested volcanic mountains, iconic rice paddies, beaches and coral reefs. The island is home to religious sites such as cliffside Uluwatu Temple. To the south, the beachside city of Kuta has lively bars, while Seminyak, Sanur and Nusa Dua are popular resort towns. The island is also known for its yoga and meditation retreats.",
                    "https://pix10.agoda.net/hotelImages/255129/-1/4be994f3bf41842cbef6626c815d18a5.jpg?s=1024x768"},
            {"Tokyo",
                    "Tokyo, Japan’s busy capital, mixes the ultramodern and the traditional, from neon-lit skyscrapers to historic temples. The opulent Meiji Shinto Shrine is known for its towering gate and surrounding woods. The Imperial Palace sits amid large public gardens. The city's many museums offer exhibits ranging from classical art (in the Tokyo National Museum) to a reconstructed kabuki theater (in the Edo-Tokyo Museum).",
                    "https://suitcasemag.com/wp-content/uploads/2019/09/city-guide-tokyo-feature.jpg"},
            {"Belanda",
                    "Amsterdam is the Netherlands’ capital, known for its artistic heritage, elaborate canal system and narrow houses with gabled facades, legacies of the city’s 17th-century Golden Age. Its Museum District houses the Van Gogh Museum, works by Rembrandt and Vermeer at the Rijksmuseum, and modern art at the Stedelijk. Cycling is key to the city’s character, and there are numerous bike paths.",
                    "https://www.holland.com/upload_mm/d/0/7/69550_fullimage_fietsen-amsterdam_1360x.jpg"},
    };

    public static ArrayList<Model_Destinasi> getListData() {
        ArrayList<Model_Destinasi> list = new ArrayList<>();
        for (String[] aData : data) {
            Model_Destinasi destinasi = new Model_Destinasi();
            destinasi.setNama_destinasi(aData[0]);
            destinasi.setDeskripssi_destinasi(aData[1]);
            destinasi.setImage_destinasi(aData[2]);
            list.add(destinasi);
        }
        return list;
    }
}
