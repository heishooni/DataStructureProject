import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static PlaylistManager manager = new PlaylistManager();

    public static void main(String[] args) {
        while (true) {
            printMenu();

            int menu = inputInt("메뉴 선택: ");

            switch (menu) {
                case 1:
                    addMusic();
                    break;
                case 2:
                    deleteMusic();
                    break;
                case 3:
                    manager.printAllMusic();
                    break;
                case 4:
                    searchByTitle();
                    break;
                case 5:
                    searchBySinger();
                    break;
                case 6:
                    setFavorite();
                    break;
                case 7:
                    printByGenre();
                    break;
                case 8:
                    manager.printFavoriteMusic();
                    break;
                case 9:
                    manager.printTotalPlayTime();
                    break;
                case 10:
                    manager.printTotalPlayTimeByGenre();
                    break;
                case 11:
                    manager.printSortedByTitle();
                    break;
                case 12:
                    manager.printTrash();
                    break;
                case 13:
                    manager.printGenreCount();
                    break;
                case 0:
                    System.out.println("프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("없는 메뉴입니다.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n===== 뮤직 플레이리스트 관리 시스템 =====");
        System.out.println("1. 음악 추가");
        System.out.println("2. 음악 삭제");
        System.out.println("3. 전체 음악 목록 출력");
        System.out.println("4. 제목으로 음악 검색");
        System.out.println("5. 가수별 음악 확인");
        System.out.println("6. 즐겨찾기 설정");
        System.out.println("7. 장르별 음악 확인");
        System.out.println("8. 즐겨찾기 음악 확인");
        System.out.println("9. 플레이리스트 총 재생시간 확인");
        System.out.println("10. 장르별 총 재생시간 확인");
        System.out.println("11. 음악 제목 오름차순 출력");
        System.out.println("12. 휴지통 확인");
        System.out.println("13. 장르별 음악 개수 확인");
        System.out.println("0. 종료");
    }

    private static void addMusic() {
        System.out.println("\n[음악 추가]");

        int id = inputInt("음악 ID: ");
        String title = inputString("제목: ");
        String singer = inputString("가수: ");
        String album = inputString("앨범명: ");
        String genre = inputString("장르: ");
        int playTime = inputInt("재생시간(초): ");

        String answer = inputString("즐겨찾기 여부(Y/N): ");
        boolean favorite = answer.equalsIgnoreCase("Y");

        Music music = new Music(id, title, singer, album, genre, playTime, favorite);

        manager.addMusic(music);
    }

    private static void deleteMusic() {
        int id = inputInt("삭제할 음악 ID: ");
        manager.deleteMusic(id);
    }

    private static void searchByTitle() {
        String keyword = inputString("검색할 제목: ");
        manager.searchByTitle(keyword);
    }

    private static void searchBySinger() {
        String singer = inputString("검색할 가수: ");
        manager.searchBySinger(singer);
    }

    private static void setFavorite() {
        int id = inputInt("즐겨찾기를 변경할 음악 ID: ");

        String answer = inputString("즐겨찾기로 설정할까요? (Y/N): ");
        boolean favorite = answer.equalsIgnoreCase("Y");

        manager.setFavorite(id, favorite);
    }

    private static void printByGenre() {
        String genre = inputString("확인할 장르: ");
        manager.printByGenre(genre);
    }

    private static String inputString(String message) {
        System.out.print(message);
        return sc.nextLine();
    }

    private static int inputInt(String message) {
        while (true) {
            System.out.print(message);

            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력하세요.");
            }
        }
    }
}