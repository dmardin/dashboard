/**
 * Store and restore the selected carousel image in browser's local storage.
 * Additionally, the trend chart is redrawn.
 *
 * @param {String} carouselId - ID of the carousel
 */
function storeAndRestoreCarousel (carouselId) {
    const carousel = $('#' + carouselId);
    carousel.on('slid.bs.carousel', function (e) {
        localStorage.setItem(carouselId, e.to);
        const chart = $(e.relatedTarget).find('>:first-child')[0].echart;
        if (chart) {
            chart.resize();
        }
    });
    const activeCarousel = localStorage.getItem(carouselId);
    if (activeCarousel) {
        carousel.carousel(parseInt(activeCarousel));
    }
}