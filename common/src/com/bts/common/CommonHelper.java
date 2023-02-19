package bts.common;

import java.util.Arrays;
import java.util.List;

public class CommonHelper {

    private static final List<String> names = Arrays.asList(
            "Charles Baudelaire", "Oscar Wilde", "George Sand", "Walt Whitman",
            "Arthur Rimbaud", "Emile Zola", "Paul Verlaine", "Victor Hugo",
            "Alfred de Musset", "Jean-Paul Sartre", "Paul Valéry", "Pierre Louÿs",
            "Paul Éluard", "Jean Cocteau", "Jean Genet", "Jacques Prévert",
            "Nikola Tesla", "Claude Shannon", "Marie Curie", "Grace Hopper");

    public static String randomName() {
        return names.get((int) (Math.random() * names.size()));
    }
}