import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;

public class PlaylistManager {
    private ArrayList<Music> musicList = new ArrayList<>();
    private ArrayList<Music> trashList = new ArrayList<>();

    private HashMap<Integer, LocalDate> trashDateMap = new HashMap<>();

    public void addMusic(Music music) {
        cleanTrash();

        if (findMusicIndexById(music.id()) != -1) {
            System.out.println("이미 존재하는 음악 ID입니다.");
            return;
        }

        musicList.add(music);
        System.out.println("음악이 추가되었습니다.");
    }

    public void deleteMusic(int id) {
        cleanTrash();

        int index = findMusicIndexById(id);

        if (index == -1) {
            System.out.println("해당 ID의 음악이 없습니다.");
            return;
        }

        Music deletedMusic = musicList.remove(index);

        trashList.add(deletedMusic);
        trashDateMap.put(deletedMusic.id(), LocalDate.now());

        System.out.println("음악이 삭제되었습니다.");
        System.out.println("삭제된 음악은 30일 동안 휴지통에 보관됩니다.");
    }

    public void printAllMusic() {
        cleanTrash();

        System.out.println("\n[전체 음악 목록]");

        if (musicList.isEmpty()) {
            System.out.println("등록된 음악이 없습니다.");
            return;
        }

        for (Music music : musicList) {
            System.out.println(music);
        }
    }

    public void searchByTitle(String keyword) {
        cleanTrash();

        System.out.println("\n[제목으로 음악 검색]");

        boolean found = false;

        for (Music music : musicList) {
            if (music.title().contains(keyword)) {
                System.out.println(music);
                found = true;
            }
        }

        if (!found) {
            System.out.println("검색 결과가 없습니다.");
        }
    }

    public void searchBySinger(String singer) {
        cleanTrash();

        System.out.println("\n[가수별 음악 확인]");

        boolean found = false;

        for (Music music : musicList) {
            if (music.singer().equals(singer)) {
                System.out.println(music);
                found = true;
            }
        }

        if (!found) {
            System.out.println("검색 결과가 없습니다.");
        }
    }

    public void setFavorite(int id, boolean favorite) {
        cleanTrash();

        int index = findMusicIndexById(id);

        if (index == -1) {
            System.out.println("해당 ID의 음악이 없습니다.");
            return;
        }

        Music oldMusic = musicList.get(index);
        Music newMusic = oldMusic.changeFavorite(favorite);

        musicList.set(index, newMusic);

        System.out.println("즐겨찾기 상태가 변경되었습니다.");
    }

    public void printByGenre(String genre) {
        cleanTrash();

        System.out.println("\n[장르별 음악 확인]");

        boolean found = false;

        for (Music music : musicList) {
            if (music.genre().equals(genre)) {
                System.out.println(music);
                found = true;
            }
        }

        if (!found) {
            System.out.println("해당 장르의 음악이 없습니다.");
        }
    }

    public void printFavoriteMusic() {
        cleanTrash();

        System.out.println("\n[즐겨찾기 음악 확인]");

        boolean found = false;

        for (Music music : musicList) {
            if (music.favorite()) {
                System.out.println(music);
                found = true;
            }
        }

        if (!found) {
            System.out.println("즐겨찾기 음악이 없습니다.");
        }
    }

    public void printTotalPlayTime() {
        cleanTrash();

        System.out.println("\n[플레이리스트 총 재생시간]");

        int total = 0;

        for (Music music : musicList) {
            total += music.playTime();
        }

        System.out.println("총 재생시간: " + formatTime(total));
    }

    public void printTotalPlayTimeByGenre() {
        cleanTrash();

        System.out.println("\n[장르별 총 재생시간]");

        HashMap<String, Integer> genreTimeMap = new HashMap<>();

        for (Music music : musicList) {
            String genre = music.genre();

            if (genreTimeMap.containsKey(genre)) {
                int oldTime = genreTimeMap.get(genre);
                genreTimeMap.put(genre, oldTime + music.playTime());
            } else {
                genreTimeMap.put(genre, music.playTime());
            }
        }

        for (String genre : genreTimeMap.keySet()) {
            System.out.println(genre + " : " + formatTime(genreTimeMap.get(genre)));
        }
    }

    public void printGenreCount() {
        cleanTrash();

        System.out.println("\n[장르별 음악 개수]");

        HashMap<String, Integer> genreCountMap = new HashMap<>();

        for (Music music : musicList) {
            String genre = music.genre();

            if (genreCountMap.containsKey(genre)) {
                int oldCount = genreCountMap.get(genre);
                genreCountMap.put(genre, oldCount + 1);
            } else {
                genreCountMap.put(genre, 1);
            }
        }

        for (String genre : genreCountMap.keySet()) {
            System.out.println(genre + " : " + genreCountMap.get(genre) + "곡");
        }
    }

    public void printSortedByTitle() {
        cleanTrash();

        System.out.println("\n[음악 제목 오름차순 출력]");

        ArrayList<Music> copyList = new ArrayList<>();

        for (Music music : musicList) {
            copyList.add(music);
        }

        for (int i = 0; i < copyList.size() - 1; i++) {
            for (int j = i + 1; j < copyList.size(); j++) {
                if (copyList.get(i).title().compareTo(copyList.get(j).title()) > 0) {
                    Music temp = copyList.get(i);
                    copyList.set(i, copyList.get(j));
                    copyList.set(j, temp);
                }
            }
        }

        for (Music music : copyList) {
            System.out.println(music);
        }
    }

    public void printTrash() {
        cleanTrash();

        System.out.println("\n[휴지통]");

        if (trashList.isEmpty()) {
            System.out.println("휴지통이 비어 있습니다.");
            return;
        }

        for (Music music : trashList) {
            LocalDate deletedDate = trashDateMap.get(music.id());
            long passedDays = ChronoUnit.DAYS.between(deletedDate, LocalDate.now());
            long leftDays = 30 - passedDays;

            System.out.println(music);
            System.out.println("삭제일: " + deletedDate);
            System.out.println("자동 삭제까지 남은 기간: " + leftDays + "일");
            System.out.println("-----------------------------");
        }
    }

    private void cleanTrash() {
        for (int i = trashList.size() - 1; i >= 0; i--) {
            Music music = trashList.get(i);
            LocalDate deletedDate = trashDateMap.get(music.id());

            long passedDays = ChronoUnit.DAYS.between(deletedDate, LocalDate.now());

            if (passedDays >= 30) {
                trashList.remove(i);
                trashDateMap.remove(music.id());
            }
        }
    }

    private int findMusicIndexById(int id) {
        for (int i = 0; i < musicList.size(); i++) {
            if (musicList.get(i).id() == id) {
                return i;
            }
        }

        return -1;
    }

    private String formatTime(int seconds) {
        int minute = seconds / 60;
        int second = seconds % 60;

        return minute + "분 " + second + "초";
    }
}