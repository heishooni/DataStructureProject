public record Music(
        int id,
        String title,
        String singer,
        String album,
        String genre,
        int playTime,
        boolean favorite
) {
    public Music changeFavorite(boolean favorite) {
        return new Music(id, title, singer, album, genre, playTime, favorite);
    }

    public String getPlayTime() {
        int minute = playTime / 60;
        int second = playTime % 60;
        return minute + "분 " + second + "초";
    }

    @Override
    public String toString() {
        return "ID: " + id +
                " | 제목: " + title +
                " | 가수: " + singer +
                " | 앨범: " + album +
                " | 장르: " + genre +
                " | 재생시간: " + getPlayTime() +
                " | 즐겨찾기: " + (favorite ? "Y" : "N");
    }
}